package com.dragon.springframework.core.annotation;

import com.dragon.springframework.core.Assert;
import com.dragon.springframework.core.ObjectUtils;
import com.dragon.springframework.core.StringUtils;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 支持别名配置的别名描述器，用于描述{@link AliasFor}细节。
 *
 * @author SuccessZhang
 * @date 2020/06/11
 */
@Getter
public class AliasDescriptor {

    private static final Map<Method, AliasDescriptor> ALIAS_DESCRIPTOR_CACHE =
            new ConcurrentHashMap<>(256);

    private final Method sourceAttribute;

    private final Class<? extends Annotation> sourceAnnotationType;

    private final String sourceAttributeName;

    private final Method[] aliasedAttributes;

    private final Class<? extends Annotation>[] aliasedAnnotationTypes;

    private final String[] aliasedAttributeNames;

    private final boolean[] isAliasPairs;

    public static AliasDescriptor from(Method attribute) {
        AliasDescriptor descriptor = ALIAS_DESCRIPTOR_CACHE.get(attribute);
        if (descriptor == null) {
            AliasFor[] array = getAliasFors(attribute);
            if (array == null) {
                return null;
            }
            descriptor = new AliasDescriptor(attribute, array);
            descriptor.validate();
            ALIAS_DESCRIPTOR_CACHE.put(attribute, descriptor);
        }
        return descriptor;
    }

    /**
     * 先尝试获取{@link AliasFors}，如果获取不到，
     * 再尝试获取{@link AliasFor}，如果能获取到，
     * 返回一个只有单个元素的数组，仍然获取不到则返回null。
     */
    private static AliasFor[] getAliasFors(AnnotatedElement element) {
        AliasFors aliasFors = element.getAnnotation(AliasFors.class);
        if (aliasFors == null) {
            AliasFor aliasFor = element.getAnnotation(AliasFor.class);
            if (aliasFor == null) {
                return null;
            }
            return new AliasFor[]{aliasFor};
        }
        return aliasFors.value();
    }

    /**
     * 构造方法私有化，希望外部使用
     * {@link #from(Method)}来创建此类的实例。
     */
    @SuppressWarnings("unchecked")
    private AliasDescriptor(Method sourceAttribute, AliasFor[] aliasFors) {
        Class<?> declaringClass = sourceAttribute.getDeclaringClass();
        Assert.isTrue(declaringClass.isAnnotation(), "sourceAttribute must be from an annotation");
        this.sourceAttribute = sourceAttribute;
        this.sourceAnnotationType = (Class<? extends Annotation>) declaringClass;
        this.sourceAttributeName = sourceAttribute.getName();
        List<Class<? extends Annotation>> aliasedAnnotationTypes = new ArrayList<>();
        for (AliasFor aliasFor : aliasFors) {
            aliasedAnnotationTypes.add(Annotation.class == aliasFor.annotation() ?
                    this.sourceAnnotationType : aliasFor.annotation());
        }
        this.aliasedAnnotationTypes = aliasedAnnotationTypes.toArray(new Class[0]);
        this.aliasedAttributeNames = getAliasedAttributeNames(aliasFors, sourceAttribute).toArray(new String[0]);
        List<Method> aliasedAttributes = new ArrayList<>();
        boolean[] isAliasPairs = new boolean[this.aliasedAnnotationTypes.length];
        for (int i = 0; i < this.aliasedAnnotationTypes.length; i++) {
            Class<? extends Annotation> aliasedAnnotationType = this.aliasedAnnotationTypes[i];
            String aliasedAttributeName = this.aliasedAttributeNames[i];
            if (aliasedAnnotationType == this.sourceAnnotationType &&
                    aliasedAttributeName.equals(this.sourceAttributeName)) {
                String msg = String.format("@AliasFor declaration on attribute '%s' in annotation [%s] points to itself. Specify 'annotation' to point to a same-named attribute on a meta-annotation.",
                        sourceAttribute.getName(), declaringClass.getName());
                throw new RuntimeException(msg);
            }
            try {
                aliasedAttributes.add(aliasedAnnotationType.getDeclaredMethod(aliasedAttributeName));
            } catch (NoSuchMethodException ex) {
                String msg = String.format(
                        "Attribute '%s' in annotation [%s] is declared as an @AliasFor nonexistent attribute '%s' in annotation [%s].",
                        this.sourceAttributeName, this.sourceAnnotationType.getName(), aliasedAttributeName,
                        aliasedAnnotationType.getName());
                throw new RuntimeException(msg, ex);
            }
            isAliasPairs[i] = this.sourceAnnotationType == aliasedAnnotationType;
        }
        this.aliasedAttributes = aliasedAttributes.toArray(new Method[0]);
        this.isAliasPairs = isAliasPairs;
    }

    /**
     * 通过注解属性上提供的{@link AliasFor}注解，
     * 获取配置的别名属性的名称；如果未指定别名，
     * 则获取原始属性的名称（指向元注解的同名属性）。
     */
    private List<String> getAliasedAttributeNames(AliasFor[] aliasFors, Method attribute) {
        List<String> aliasedAttributeNames = new ArrayList<>();
        for (AliasFor aliasFor : aliasFors) {
            String attributeName = aliasFor.attribute();
            String value = aliasFor.value();
            //可以理解为attributeName不是null并且不是""，就会返回true
            boolean attributeDeclared = StringUtils.hasText(attributeName);
            boolean valueDeclared = StringUtils.hasText(value);
            if (attributeDeclared && valueDeclared) {
                String msg = String.format("In @AliasFor declared on attribute '%s' in annotation [%s], attribute 'attribute' and its alias 'value' are present with values of [%s] and [%s], but only one is permitted.",
                        attribute.getName(), attribute.getDeclaringClass().getName(), attributeName, value);
                throw new RuntimeException(msg);
            }
            attributeName = (attributeDeclared ? attributeName : value);
            aliasedAttributeNames.add(StringUtils.hasText(attributeName) ? attributeName.trim() : attribute.getName());
        }
        return aliasedAttributeNames;
    }

    /**
     * 合法性校验。
     */
    private void validate() {
        for (int i = 0; i < this.isAliasPairs.length; i++) {
            boolean isAliasPair = this.isAliasPairs[i];
            //isAnnotationMetaPresent()如果有指定的元注解返回true。
            if (!isAliasPair && !AnnotationUtils.isAnnotationMetaPresent(this.sourceAnnotationType, this.aliasedAnnotationTypes[i])) {
                String msg = String.format("@AliasFor declaration on attribute '%s' in annotation [%s] declares an alias for attribute '%s' in meta-annotation [%s] which is not meta-present.",
                        this.sourceAttributeName, this.sourceAnnotationType.getName(), this.aliasedAttributeNames[i],
                        this.aliasedAnnotationTypes[i].getName());
                throw new RuntimeException(msg);
            }
            if (isAliasPair) {
                AliasFor[] mirrorAliasFors = getAliasFors(this.aliasedAttributes[i]);
                if (mirrorAliasFors == null) {
                    String msg = String.format("Attribute '%s' in annotation [%s] must be declared as an @AliasFor [%s].",
                            this.aliasedAttributeNames[i], this.sourceAnnotationType.getName(), this.sourceAttributeName);
                    throw new RuntimeException(msg);
                }
                List<String> mirrorAliasedAttributeNames = getAliasedAttributeNames(mirrorAliasFors, this.aliasedAttributes[i]);
                if (!mirrorAliasedAttributeNames.contains(this.sourceAttributeName)) {
                    String msg = String.format("Attribute '%s' in annotation [%s] must be declared as an @AliasFor [%s], not just %s.",
                            this.aliasedAttributeNames[i], this.sourceAnnotationType.getName(), this.sourceAttributeName,
                            mirrorAliasedAttributeNames.toString());
                    throw new RuntimeException(msg);
                }
            }
            Class<?> returnType = this.sourceAttribute.getReturnType();
            Class<?> aliasedReturnType = this.aliasedAttributes[i].getReturnType();
            if (returnType != aliasedReturnType &&
                    (!aliasedReturnType.isArray() || returnType != aliasedReturnType.getComponentType())) {
                String msg = String.format("Misconfigured aliases: attribute '%s' in annotation [%s] and attribute '%s' in annotation [%s] must declare the same return type.",
                        this.sourceAttributeName, this.sourceAnnotationType.getName(), this.aliasedAttributeNames[i],
                        this.aliasedAnnotationTypes[i].getName());
                throw new RuntimeException(msg);
            }
            if (isAliasPair) {
                validateDefaultValueConfiguration(this.aliasedAttributes[i]);
            }
        }
    }

    /**
     * 注解中用default声明的默认值合法性校验。
     */
    private void validateDefaultValueConfiguration(Method aliasedAttribute) {
        Assert.notNull(aliasedAttribute, "aliasedAttribute must not be null");
        Object defaultValue = this.sourceAttribute.getDefaultValue();
        Object aliasedDefaultValue = aliasedAttribute.getDefaultValue();
        if (defaultValue == null || aliasedDefaultValue == null) {
            String msg = String.format("Misconfigured aliases: attribute '%s' in annotation [%s] and attribute '%s' in annotation [%s] must declare default values.",
                    this.sourceAttributeName, this.sourceAnnotationType.getName(), aliasedAttribute.getName(),
                    aliasedAttribute.getDeclaringClass().getName());
            throw new RuntimeException(msg);
        }
        // 如果都为null返回true，如果只有一个为null返回false，其他情况
        // 用Arrays.equals比较数组，并根据数组元素而不是数组引用执行相等检查。
        if (!ObjectUtils.nullSafeEquals(defaultValue, aliasedDefaultValue)) {
            String msg = String.format("Misconfigured aliases: attribute '%s' in annotation [%s] and attribute '%s' in annotation [%s] must declare the same default value.",
                    this.sourceAttributeName, this.sourceAnnotationType.getName(), aliasedAttribute.getName(),
                    aliasedAttribute.getDeclaringClass().getName());
            throw new RuntimeException(msg);
        }
    }

    /**
     * 获取属性别名列表。
     */
    public List<String> getAttributeAliasNames() {
        List<String> aliases = new ArrayList<>();
        for (AliasDescriptor otherDescriptor : getOtherDescriptors()) {
            if (this.isAliasFor(otherDescriptor)) {
                this.validateDefaultValueConfiguration(otherDescriptor.sourceAttribute);
                aliases.add(otherDescriptor.sourceAttributeName);
            }
        }
        return aliases;
    }

    /**
     * 获取源注解的所有属性别名描述器。
     */
    private List<AliasDescriptor> getOtherDescriptors() {
        List<AliasDescriptor> otherDescriptors = new ArrayList<>();
        //getAttributeMethods()获取注解上所有的属性方法。
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

    /**
     * 获取属性覆盖描述符。
     */
    private List<AliasDescriptor> getAttributeOverrideDescriptor() {
        List<AliasDescriptor> result = new ArrayList<>();
        for (int i = 0; i < this.aliasedAttributes.length; i++) {
            Method aliasedAttribute = this.aliasedAttributes[i];
            if (this.isAliasPairs[i]) {
                continue;
            }
            result.add(AliasDescriptor.from(aliasedAttribute));
        }
        return result;
    }

    /**
     * 确定此描述符和提供的描述符是否有效地显式或隐式表示
     * 同一目标注解中同一属性的别名，此方法搜索以该描述符开头的
     * 属性重写层次结构，以便检测隐式和可传递的隐式别名。
     */
    private boolean isAliasFor(AliasDescriptor otherDescriptor) {
        Queue<AliasDescriptor> lhs = new LinkedList<>();
        lhs.offer(this);
        while (!lhs.isEmpty()) {
            AliasDescriptor lh = lhs.poll();
            Queue<AliasDescriptor> rhs = new LinkedList<>();
            rhs.offer(otherDescriptor);
            while (!rhs.isEmpty()) {
                AliasDescriptor rh = rhs.poll();
                for (Method lhAliasedAttribute : lh.aliasedAttributes) {
                    for (Method rhAliasedAttribute : rh.aliasedAttributes) {
                        if (lhAliasedAttribute.equals(rhAliasedAttribute)) {
                            return true;
                        }
                    }
                }
                rhs.addAll(rh.getAttributeOverrideDescriptor());
            }
            lhs.addAll(lh.getAttributeOverrideDescriptor());
        }
        return false;
    }

    /**
     * 获取通过{@link AliasFor}配置的
     * 同层次覆盖属性的名称。
     */
    String[] getAttributeOverrideName(Class<? extends Annotation> metaAnnotationType) {
        Assert.notNull(metaAnnotationType, "metaAnnotationType must not be null");
        Assert.isTrue(Annotation.class != metaAnnotationType,
                "metaAnnotationType must not be [java.lang.annotation.Annotation]");
        List<String> result = new ArrayList<>();
        Queue<AliasDescriptor> queue = new LinkedList<>();
        queue.offer(this);
        while (!queue.isEmpty()) {
            AliasDescriptor desc = queue.poll();
            if (desc == null) {
                continue;
            }
            //确定此描述符是否代表提供的元注解中属性的显式替代。
            if (desc.aliasedAnnotationTypes != null) {
                for (int i = 0; i < desc.aliasedAnnotationTypes.length; i++) {
                    if (desc.aliasedAnnotationTypes[i] == metaAnnotationType) {
                        result.add(desc.aliasedAttributeNames[i]);
                    }
                }
            }
            queue.addAll(desc.getAttributeOverrideDescriptor());
        }
        return result.toArray(new String[0]);
    }
}