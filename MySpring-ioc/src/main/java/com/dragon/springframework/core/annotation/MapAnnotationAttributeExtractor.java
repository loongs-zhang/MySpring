package com.dragon.springframework.core.annotation;

import com.dragon.springframework.core.Assert;
import com.dragon.springframework.core.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link AnnotationAttributeExtractor} strategy that
 * is backed by a {@link Map}.
 *
 * @author Sam Brannen
 * @see Annotation
 * @see AliasFor
 * @see AbstractAliasAwareAnnotationAttributeExtractor
 * @see DefaultAnnotationAttributeExtractor
 * @see AnnotationUtils#synthesizeAnnotation(Map, Class, AnnotatedElement)
 * @since 4.2
 */
class MapAnnotationAttributeExtractor extends AbstractAliasAwareAnnotationAttributeExtractor<Map<String, Object>> {

    MapAnnotationAttributeExtractor(Map<String, Object> attributes, Class<? extends Annotation> annotationType,
                                    AnnotatedElement annotatedElement) {
        super(annotationType, annotatedElement, enrichAndValidateAttributes(attributes, annotationType));
    }

    @Override
    protected Object getRawAttributeValue(Method attributeMethod) {
        return getRawAttributeValue(attributeMethod.getName());
    }

    @Override
    protected Object getRawAttributeValue(String attributeName) {
        return getSource().get(attributeName);
    }

    /**
     * Enrich and validate the supplied <em>attributes</em> map by ensuring
     * that it contains a non-null entry for each annotation attribute in
     * the specified {@code annotationType} and that the type of the entry
     * matches the return type for the corresponding annotation attribute.
     * <p>If an entry is a map (presumably of annotation attributes), an
     * attempt will be made to synthesize an annotation from it. Similarly,
     * if an entry is an array of maps, an attempt will be made to synthesize
     * an array of annotations from those maps.
     * <p>If an attribute is missing in the supplied map, it will be set
     * either to the value of its alias (if an alias exists) or to the
     * value of the attribute's default value (if defined), and otherwise
     * an {@link IllegalArgumentException} will be thrown.
     */
    @SuppressWarnings("unchecked")
    private static Map<String, Object> enrichAndValidateAttributes(
            Map<String, Object> originalAttributes, Class<? extends Annotation> annotationType) {
        Map<String, Object> attributes = new LinkedHashMap<>(originalAttributes);
        Map<String, List<String>> attributeAliasMap = AnnotationUtils.getAttributeAliasMap(annotationType);
        for (Method attributeMethod : AnnotationUtils.getAttributeMethods(annotationType)) {
            String attributeName = attributeMethod.getName();
            Object attributeValue = attributes.get(attributeName);
            if (attributeValue == null) {
                // if attribute not present, check aliases
                List<String> aliasNames = attributeAliasMap.get(attributeName);
                if (aliasNames != null) {
                    for (String aliasName : aliasNames) {
                        Object aliasValue = attributes.get(aliasName);
                        if (aliasValue != null) {
                            attributeValue = aliasValue;
                            attributes.put(attributeName, attributeValue);
                            break;
                        }
                    }
                }
            }
            if (attributeValue == null) {
                // if aliases not present, check default
                Object defaultValue = AnnotationUtils.getDefaultValue(annotationType, attributeName);
                if (defaultValue != null) {
                    attributeValue = defaultValue;
                    attributes.put(attributeName, attributeValue);
                }
            }
            Assert.notNull(attributeValue, () -> String.format(
                    "Attributes map %s returned null for required attribute '%s' defined by annotation type [%s].",
                    attributes, attributeName, annotationType.getName()));
            // if still null, ensure correct type
            Class<?> requiredReturnType = attributeMethod.getReturnType();
            Class<?> actualReturnType = attributeValue.getClass();
            if (!ClassUtils.isAssignable(requiredReturnType, actualReturnType)) {
                boolean converted = false;
                if (requiredReturnType.isArray() &&
                        requiredReturnType.getComponentType() == actualReturnType) {
                    // Single element overriding an array of the same type?
                    Object array = Array.newInstance(requiredReturnType.getComponentType(), 1);
                    Array.set(array, 0, attributeValue);
                    attributes.put(attributeName, array);
                    converted = true;
                } else if (Annotation.class.isAssignableFrom(requiredReturnType) &&
                        Map.class.isAssignableFrom(actualReturnType)) {
                    // Nested map representing a single annotation?
                    Class<? extends Annotation> nestedAnnotationType =
                            (Class<? extends Annotation>) requiredReturnType;
                    Map<String, Object> map = (Map<String, Object>) attributeValue;
                    attributes.put(attributeName, AnnotationUtils.synthesizeAnnotation(map, nestedAnnotationType, null));
                    converted = true;
                } else if (requiredReturnType.isArray() && actualReturnType.isArray() &&
                        Annotation.class.isAssignableFrom(requiredReturnType.getComponentType()) &&
                        Map.class.isAssignableFrom(actualReturnType.getComponentType())) {
                    // Nested array of maps representing an array of annotations?
                    Class<? extends Annotation> nestedAnnotationType =
                            (Class<? extends Annotation>) requiredReturnType.getComponentType();
                    Map<String, Object>[] maps = (Map<String, Object>[]) attributeValue;
                    attributes.put(attributeName,
                            AnnotationUtils.synthesizeAnnotationArray(maps, nestedAnnotationType));
                    converted = true;
                }
                Assert.isTrue(converted, () -> String.format(
                        "Attributes map %s returned a value of type [%s] for attribute '%s', " +
                                "but a value of type [%s] is required as defined by annotation type [%s].",
                        attributes, actualReturnType.getName(), attributeName, requiredReturnType.getName(),
                        annotationType.getName()));
            }
        }
        return attributes;
    }

}
