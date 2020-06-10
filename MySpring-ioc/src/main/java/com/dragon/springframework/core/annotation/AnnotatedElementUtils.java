package com.dragon.springframework.core.annotation;

import com.dragon.springframework.core.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("all")
public class AnnotatedElementUtils {

    public static <A extends Annotation> A getMergedAnnotation(AnnotatedElement element, Class<A> annotationType) {
        Assert.notNull(annotationType, "'annotationType' must not be null");
        if (element instanceof Class) {
            // Exhaustive retrieval of merged annotation attributes...
            AnnotationAttributes attributes = getMergedAnnotationAttributes(element, annotationType);
            return (attributes != null ? AnnotationUtils.synthesizeAnnotation(attributes, annotationType, element) : null);
        }
        // Do not use this shortcut against a Class: Inherited annotations
        // would get preferred over locally declared composed annotations.
        A annotation = element.getAnnotation(annotationType);
        if (annotation != null) {
            return AnnotationUtils.synthesizeAnnotation(annotation, element);
        }
        return null;
    }

    public static AnnotationAttributes getMergedAnnotationAttributes(
            AnnotatedElement element, Class<? extends Annotation> annotationType) {
        Assert.notNull(annotationType, "'annotationType' must not be null");
        AnnotationAttributes attributes = searchWithGetSemantics(element, annotationType, new HashSet<>());
        AnnotationUtils.postProcessAnnotationAttributes(element, attributes, false, false);
        return attributes;
    }

    private static AnnotationAttributes searchWithGetSemantics(AnnotatedElement element,
                                                               Class<? extends Annotation> annotationType,
                                                               Set<AnnotatedElement> visited) {
        Assert.notNull(element, "AnnotatedElement must not be null");
        AnnotationAttributes result = null;
        if (visited.add(element)) {
            try {
                // Start searching within locally declared annotations
                Annotation[] annotations = element.getAnnotations();
                for (Annotation annotation : annotations) {
                    Class<? extends Annotation> currentAnnotationType = annotation.annotationType();
                    if (AnnotationUtils.isInJavaLangAnnotationPackage(currentAnnotationType)) {
                        continue;
                    }
                    if (currentAnnotationType == annotationType) {
                        result = AnnotationUtils.retrieveAnnotationAttributes(element,
                                annotation, false, false);
                        if (result != null) {
                            return result;
                        }
                    }
                    // Recursively search in meta-annotations
                    result = searchWithGetSemantics(currentAnnotationType, annotationType, visited);
                    if (result != null) {
                        postProcess(element, annotation, result);
                        return result;
                    }
                }
            } catch (Throwable ignored) {
            }
        }
        return result;
    }

    private static void postProcess(AnnotatedElement element, Annotation annotation, AnnotationAttributes attributes) {
        annotation = AnnotationUtils.synthesizeAnnotation(annotation, element);
        Class<? extends Annotation> targetAnnotationType = attributes.annotationType();
        // Track which attribute values have already been replaced so that we can short
        // circuit the search algorithms.
        Set<String> valuesAlreadyReplaced = new HashSet<>();
        for (Method attributeMethod : AnnotationUtils.getAttributeMethods(annotation.annotationType())) {
            String attributeName = attributeMethod.getName();
            String attributeOverrideName = AnnotationUtils.getAttributeOverrideName(attributeMethod, targetAnnotationType);
            // Explicit annotation attribute override declared via @AliasFor
            if (attributeOverrideName != null) {
                if (valuesAlreadyReplaced.contains(attributeOverrideName)) {
                    continue;
                }
                List<String> targetAttributeNames = new ArrayList<>();
                targetAttributeNames.add(attributeOverrideName);
                valuesAlreadyReplaced.add(attributeOverrideName);
                // Ensure all aliased attributes in the target annotation are overridden. (SPR-14069)
                List<String> aliases = AnnotationUtils.getAttributeAliasMap(targetAnnotationType).get(attributeOverrideName);
                if (aliases != null && !aliases.isEmpty()) {
                    for (String alias : aliases) {
                        if (!valuesAlreadyReplaced.contains(alias)) {
                            targetAttributeNames.add(alias);
                            valuesAlreadyReplaced.add(alias);
                        }
                    }
                }
                for (String targetAttributeName : targetAttributeNames) {
                    attributes.put(targetAttributeName,
                            AnnotationUtils.getAdaptedValue(element, annotation, attributeName));
                }
            } else if (!"value".equals(attributeName) &&
                    attributes.containsKey(attributeName)) {
                // Implicit annotation attribute override based on convention
                attributes.put(attributeName,
                        AnnotationUtils.getAdaptedValue(element, annotation, attributeName));
            }
        }
    }
}
