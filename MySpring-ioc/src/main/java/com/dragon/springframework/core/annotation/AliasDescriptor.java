package com.dragon.springframework.core.annotation;

import com.dragon.springframework.core.Assert;
import com.dragon.springframework.core.ObjectUtils;
import com.dragon.springframework.core.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("all")
public class AliasDescriptor {

    private static final Map<Method, AliasDescriptor> aliasDescriptorCache =
            new ConcurrentHashMap<>(256);

    private final Method sourceAttribute;

    private final Class<? extends Annotation> sourceAnnotationType;

    private final String sourceAttributeName;

    private final Method aliasedAttribute;

    private final Class<? extends Annotation> aliasedAnnotationType;

    private final String aliasedAttributeName;

    private final boolean isAliasPair;

    public static AliasDescriptor from(Method attribute) {
        AliasDescriptor descriptor = aliasDescriptorCache.get(attribute);
        if (descriptor == null) {
            AliasFor aliasFor = attribute.getAnnotation(AliasFor.class);
            if (aliasFor == null) {
                return null;
            }
            descriptor = new AliasDescriptor(attribute, aliasFor);
            descriptor.validate();
            aliasDescriptorCache.put(attribute, descriptor);
        }
        return descriptor;
    }

    @SuppressWarnings("unchecked")
    private AliasDescriptor(Method sourceAttribute, AliasFor aliasFor) {
        Class<?> declaringClass = sourceAttribute.getDeclaringClass();
        Assert.isTrue(declaringClass.isAnnotation(), "sourceAttribute must be from an annotation");
        this.sourceAttribute = sourceAttribute;
        this.sourceAnnotationType = (Class<? extends Annotation>) declaringClass;
        this.sourceAttributeName = sourceAttribute.getName();
        this.aliasedAnnotationType = (Annotation.class == aliasFor.annotation() ?
                this.sourceAnnotationType : aliasFor.annotation());
        this.aliasedAttributeName = getAliasedAttributeName(aliasFor, sourceAttribute);
        if (this.aliasedAnnotationType == this.sourceAnnotationType &&
                this.aliasedAttributeName.equals(this.sourceAttributeName)) {
            String msg = String.format("@AliasFor declaration on attribute '%s' in annotation [%s] points to " +
                            "itself. Specify 'annotation' to point to a same-named attribute on a meta-annotation.",
                    sourceAttribute.getName(), declaringClass.getName());
            throw new RuntimeException(msg);
        }
        try {
            this.aliasedAttribute = this.aliasedAnnotationType.getDeclaredMethod(this.aliasedAttributeName);
        } catch (NoSuchMethodException ex) {
            String msg = String.format(
                    "Attribute '%s' in annotation [%s] is declared as an @AliasFor nonexistent attribute '%s' in annotation [%s].",
                    this.sourceAttributeName, this.sourceAnnotationType.getName(), this.aliasedAttributeName,
                    this.aliasedAnnotationType.getName());
            throw new RuntimeException(msg, ex);
        }
        this.isAliasPair = (this.sourceAnnotationType == this.aliasedAnnotationType);
    }

    private String getAliasedAttributeName(AliasFor aliasFor, Method attribute) {
        String attributeName = aliasFor.attribute();
        String value = aliasFor.value();
        boolean attributeDeclared = StringUtils.hasText(attributeName);
        boolean valueDeclared = StringUtils.hasText(value);
        if (attributeDeclared && valueDeclared) {
            String msg = String.format("In @AliasFor declared on attribute '%s' in annotation [%s], attribute 'attribute' " +
                            "and its alias 'value' are present with values of [%s] and [%s], but only one is permitted.",
                    attribute.getName(), attribute.getDeclaringClass().getName(), attributeName, value);
            throw new RuntimeException(msg);
        }
        attributeName = (attributeDeclared ? attributeName : value);
        return (StringUtils.hasText(attributeName) ? attributeName.trim() : attribute.getName());
    }

    private void validate() {
        if (!this.isAliasPair && !AnnotationUtils.isAnnotationMetaPresent(this.sourceAnnotationType, this.aliasedAnnotationType)) {
            String msg = String.format("@AliasFor declaration on attribute '%s' in annotation [%s] declares " +
                            "an alias for attribute '%s' in meta-annotation [%s] which is not meta-present.",
                    this.sourceAttributeName, this.sourceAnnotationType.getName(), this.aliasedAttributeName,
                    this.aliasedAnnotationType.getName());
            throw new RuntimeException(msg);
        }
        if (this.isAliasPair) {
            AliasFor mirrorAliasFor = this.aliasedAttribute.getAnnotation(AliasFor.class);
            if (mirrorAliasFor == null) {
                String msg = String.format("Attribute '%s' in annotation [%s] must be declared as an @AliasFor [%s].",
                        this.aliasedAttributeName, this.sourceAnnotationType.getName(), this.sourceAttributeName);
                throw new RuntimeException(msg);
            }
            String mirrorAliasedAttributeName = getAliasedAttributeName(mirrorAliasFor, this.aliasedAttribute);
            if (!this.sourceAttributeName.equals(mirrorAliasedAttributeName)) {
                String msg = String.format("Attribute '%s' in annotation [%s] must be declared as an @AliasFor [%s], not [%s].",
                        this.aliasedAttributeName, this.sourceAnnotationType.getName(), this.sourceAttributeName,
                        mirrorAliasedAttributeName);
                throw new RuntimeException(msg);
            }
        }
        Class<?> returnType = this.sourceAttribute.getReturnType();
        Class<?> aliasedReturnType = this.aliasedAttribute.getReturnType();
        if (returnType != aliasedReturnType &&
                (!aliasedReturnType.isArray() || returnType != aliasedReturnType.getComponentType())) {
            String msg = String.format("Misconfigured aliases: attribute '%s' in annotation [%s] " +
                            "and attribute '%s' in annotation [%s] must declare the same return type.",
                    this.sourceAttributeName, this.sourceAnnotationType.getName(), this.aliasedAttributeName,
                    this.aliasedAnnotationType.getName());
            throw new RuntimeException(msg);
        }
        if (this.isAliasPair) {
            validateDefaultValueConfiguration(this.aliasedAttribute);
        }
    }

    private void validateDefaultValueConfiguration(Method aliasedAttribute) {
        Assert.notNull(aliasedAttribute, "aliasedAttribute must not be null");
        Object defaultValue = this.sourceAttribute.getDefaultValue();
        Object aliasedDefaultValue = aliasedAttribute.getDefaultValue();
        if (defaultValue == null || aliasedDefaultValue == null) {
            String msg = String.format("Misconfigured aliases: attribute '%s' in annotation [%s] " +
                            "and attribute '%s' in annotation [%s] must declare default values.",
                    this.sourceAttributeName, this.sourceAnnotationType.getName(), aliasedAttribute.getName(),
                    aliasedAttribute.getDeclaringClass().getName());
            throw new RuntimeException(msg);
        }
        if (!ObjectUtils.nullSafeEquals(defaultValue, aliasedDefaultValue)) {
            String msg = String.format("Misconfigured aliases: attribute '%s' in annotation [%s] " +
                            "and attribute '%s' in annotation [%s] must declare the same default value.",
                    this.sourceAttributeName, this.sourceAnnotationType.getName(), aliasedAttribute.getName(),
                    aliasedAttribute.getDeclaringClass().getName());
            throw new RuntimeException(msg);
        }
    }

    /**
     * Determine if this descriptor represents an explicit override for
     * an attribute in the supplied {@code metaAnnotationType}.
     *
     * @see #isAliasFor
     */
    private boolean isOverrideFor(Class<? extends Annotation> metaAnnotationType) {
        return (this.aliasedAnnotationType == metaAnnotationType);
    }

    /**
     * Determine if this descriptor and the supplied descriptor both
     * effectively represent aliases for the same attribute in the same
     * target annotation, either explicitly or implicitly.
     * <p>This method searches the attribute override hierarchy, beginning
     * with this descriptor, in order to detect implicit and transitively
     * implicit aliases.
     *
     * @return {@code true} if this descriptor and the supplied descriptor
     * effectively alias the same annotation attribute
     */
    private boolean isAliasFor(AliasDescriptor otherDescriptor) {
        AliasDescriptor lhs = this;
        while (lhs != null) {
            AliasDescriptor rhs = otherDescriptor;
            while (rhs != null) {
                if (lhs.aliasedAttribute.equals(rhs.aliasedAttribute)) {
                    return true;
                }
                rhs = rhs.getAttributeOverrideDescriptor();
            }
            lhs = lhs.getAttributeOverrideDescriptor();
        }
        return false;
    }

    public List<String> getAttributeAliasNames() {
        if (this.isAliasPair) {
            return Collections.singletonList(this.aliasedAttributeName);
        }
        List<String> aliases = new ArrayList<>();
        for (AliasDescriptor otherDescriptor : getOtherDescriptors()) {
            if (this.isAliasFor(otherDescriptor)) {
                this.validateDefaultValueConfiguration(otherDescriptor.sourceAttribute);
                aliases.add(otherDescriptor.sourceAttributeName);
            }
        }
        return aliases;
    }

    private List<AliasDescriptor> getOtherDescriptors() {
        List<AliasDescriptor> otherDescriptors = new ArrayList<>();
        for (Method currentAttribute : AnnotationUtils.getAttributeMethods(this.sourceAnnotationType)) {
            if (!this.sourceAttribute.equals(currentAttribute)) {
                AliasDescriptor otherDescriptor = AliasDescriptor.from(currentAttribute);
                if (otherDescriptor != null) {
                    otherDescriptors.add(otherDescriptor);
                }
            }
        }
        return otherDescriptors;
    }

    public String getAttributeOverrideName(Class<? extends Annotation> metaAnnotationType) {
        Assert.notNull(metaAnnotationType, "metaAnnotationType must not be null");
        Assert.isTrue(Annotation.class != metaAnnotationType,
                "metaAnnotationType must not be [java.lang.annotation.Annotation]");
        AliasDescriptor desc = this;
        while (desc != null) {
            if (desc.isOverrideFor(metaAnnotationType)) {
                return desc.aliasedAttributeName;
            }
            desc = desc.getAttributeOverrideDescriptor();
        }
        return null;
    }

    private AliasDescriptor getAttributeOverrideDescriptor() {
        if (this.isAliasPair) {
            return null;
        }
        return AliasDescriptor.from(this.aliasedAttribute);
    }

    @Override
    public String toString() {
        return String.format("%s: @%s(%s) is an alias for @%s(%s)", getClass().getSimpleName(),
                this.sourceAnnotationType.getSimpleName(), this.sourceAttributeName,
                this.aliasedAnnotationType.getSimpleName(), this.aliasedAttributeName);
    }
}