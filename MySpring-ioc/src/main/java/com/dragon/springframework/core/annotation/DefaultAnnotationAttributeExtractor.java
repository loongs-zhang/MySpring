package com.dragon.springframework.core.annotation;

import com.dragon.springframework.core.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Default implementation of the {@link AnnotationAttributeExtractor} strategy
 * that is backed by an {@link Annotation}.
 *
 * @author Sam Brannen
 * @since 4.2
 */
class DefaultAnnotationAttributeExtractor extends AbstractAliasAwareAnnotationAttributeExtractor<Annotation> {

    DefaultAnnotationAttributeExtractor(Annotation annotation, Object annotatedElement) {
        super(annotation.annotationType(), annotatedElement, annotation);
    }

    @Override
    protected Object getRawAttributeValue(Method attributeMethod) {
        ReflectionUtils.makeAccessible(attributeMethod);
        return ReflectionUtils.invokeMethod(attributeMethod, getSource());
    }

    @Override
    protected Object getRawAttributeValue(String attributeName) {
        Method attributeMethod = ReflectionUtils.findMethod(getAnnotationType(), attributeName);
        return (attributeMethod != null ? getRawAttributeValue(attributeMethod) : null);
    }

}
