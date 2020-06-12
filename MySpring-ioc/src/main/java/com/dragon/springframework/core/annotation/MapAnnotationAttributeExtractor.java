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
 * 由{@link Map}支持的{@link AnnotationAttributeExtractor}策略的实现。
 * 这里作者为了方便实现，简化掉了默认实现
 * {@code DefaultAnnotationAttributeExtractor}
 *
 * @author SuccessZhang
 * @date 2020/06/12
 */
class MapAnnotationAttributeExtractor implements AnnotationAttributeExtractor {

    private final Class<? extends Annotation> annotationType;

    private final AnnotatedElement annotatedElement;

    private final Map<String, Object> attributes;

    MapAnnotationAttributeExtractor(Class<? extends Annotation> annotationType,
                                    AnnotatedElement annotatedElement,
                                    Map<String, Object> attributes) {
        this.annotationType = annotationType;
        this.annotatedElement = annotatedElement;
        this.attributes = enrichAndValidateAttributes(attributes, annotationType);
    }

    /**
     * 确保包含指定的注解类型中每个注解属性的非空条目，
     * 并且确保条目的类型与返回类型匹配。
     * 如果条目是Map（可能是注解属性），则尝试从中合成注解。
     * 类似地，如果条目是Map数组，则将尝试从这些地图合成注解数组。
     * 如果提供的映射中缺少属性，则将其设置为别名的值（如果存在别名）
     * 或属性默认值的值（如果已定义），
     * 否则将会抛出{@link IllegalArgumentException}。
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> enrichAndValidateAttributes(
            Map<String, Object> originalAttributes, Class<? extends Annotation> annotationType) {
        Map<String, Object> attributes = new LinkedHashMap<>(originalAttributes);
        Map<String, List<String>> attributeAliasMap = AnnotationUtils.getAttributeAliasMap(annotationType);
        for (Method attributeMethod : AnnotationUtils.getAttributeMethods(annotationType)) {
            String attributeName = attributeMethod.getName();
            Object attributeValue = attributes.get(attributeName);
            if (attributeValue == null) {
                // 如果属性不存在，检查别名
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
                // 如果别名不存在，检查默认值
                Object defaultValue = AnnotationUtils.getDefaultValue(annotationType, attributeName);
                if (defaultValue != null) {
                    attributeValue = defaultValue;
                    attributes.put(attributeName, attributeValue);
                }
            }
            Assert.notNull(attributeValue, () -> String.format(
                    "Attributes map %s returned null for required attribute '%s' defined by annotation type [%s].",
                    attributes, attributeName, annotationType.getName()));
            // 如果仍然为空，确保输入正确的类型
            Class<?> requiredReturnType = attributeMethod.getReturnType();
            Class<?> actualReturnType = attributeValue.getClass();
            if (ClassUtils.isAssignable(requiredReturnType, actualReturnType)) {
                continue;
            }
            boolean converted = false;
            if (requiredReturnType.isArray() &&
                    requiredReturnType.getComponentType() == actualReturnType) {
                // 普通数组类型
                Object array = Array.newInstance(requiredReturnType.getComponentType(), 1);
                Array.set(array, 0, attributeValue);
                attributes.put(attributeName, array);
                converted = true;
            } else if (Annotation.class.isAssignableFrom(requiredReturnType) &&
                    Map.class.isAssignableFrom(actualReturnType)) {
                // 注解类型
                Class<? extends Annotation> nestedAnnotationType =
                        (Class<? extends Annotation>) requiredReturnType;
                Map<String, Object> map = (Map<String, Object>) attributeValue;
                attributes.put(attributeName, AnnotationUtils.synthesizeAnnotation(map, nestedAnnotationType, null));
                converted = true;
            } else if (requiredReturnType.isArray() && actualReturnType.isArray() &&
                    Annotation.class.isAssignableFrom(requiredReturnType.getComponentType()) &&
                    Map.class.isAssignableFrom(actualReturnType.getComponentType())) {
                // 注解数组类型
                Class<? extends Annotation> nestedAnnotationType =
                        (Class<? extends Annotation>) requiredReturnType.getComponentType();
                Map<String, Object>[] maps = (Map<String, Object>[]) attributeValue;
                attributes.put(attributeName,
                        AnnotationUtils.synthesizeAnnotationArray(maps, nestedAnnotationType));
                converted = true;
            }
            Assert.isTrue(converted, () -> String.format(
                    "Attributes map %s returned a value of type [%s] for attribute '%s', but a value of type [%s] is required as defined by annotation type [%s].",
                    attributes, actualReturnType.getName(), attributeName, requiredReturnType.getName(),
                    annotationType.getName()));
        }
        return attributes;
    }

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return this.annotationType;
    }

    @Override
    public AnnotatedElement getAnnotatedElement() {
        return this.annotatedElement;
    }

    @Override
    public Object getAttributeValue(String attribute) {
        return this.attributes.get(attribute);
    }
}
