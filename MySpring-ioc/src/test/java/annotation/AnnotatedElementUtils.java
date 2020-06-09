package annotation;

import annotation.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnnotatedElementUtils {

    private static final Annotation[] EMPTY_ANNOTATION_ARRAY = new Annotation[0];

    public static <A extends Annotation> A getMergedAnnotation(AnnotatedElement element, Class<A> annotationType) {
        Assert.notNull(annotationType, "'annotationType' must not be null");

        // Shortcut: directly present on the element, with no merging needed?
        if (!(element instanceof Class)) {
            // Do not use this shortcut against a Class: Inherited annotations
            // would get preferred over locally declared composed annotations.
            A annotation = element.getAnnotation(annotationType);
            if (annotation != null) {
                return AnnotationUtils.synthesizeAnnotation(annotation, element);
            }
        }

        // Exhaustive retrieval of merged annotation attributes...
        AnnotationAttributes attributes = getMergedAnnotationAttributes(element, annotationType);
        return (attributes != null ? AnnotationUtils.synthesizeAnnotation(attributes, annotationType, element) : null);
    }

    public static AnnotationAttributes getMergedAnnotationAttributes(
            AnnotatedElement element, Class<? extends Annotation> annotationType) {

        Assert.notNull(annotationType, "'annotationType' must not be null");
        AnnotationAttributes attributes = searchWithGetSemantics(element, annotationType, null,
                new MergedAnnotationAttributesProcessor());
        AnnotationUtils.postProcessAnnotationAttributes(element, attributes, false, false);
        return attributes;
    }

    private static <T> T searchWithGetSemantics(AnnotatedElement element,
                                                Class<? extends Annotation> annotationType,
                                                String annotationName, Processor<T> processor) {

        return searchWithGetSemantics(element, annotationType, annotationName, null, processor);
    }

    private static <T> T searchWithGetSemantics(AnnotatedElement element,
                                                Class<? extends Annotation> annotationType, String annotationName,
                                                Class<? extends Annotation> containerType, Processor<T> processor) {

        try {
            return searchWithGetSemantics(element, annotationType, annotationName, containerType, processor,
                    new HashSet<>(), 0);
        } catch (Throwable ex) {
            throw new IllegalStateException("Failed to introspect annotations on " + element, ex);
        }
    }

    private static <T> T searchWithGetSemantics(AnnotatedElement element,
                                                Class<? extends Annotation> annotationType, String annotationName,
                                                Class<? extends Annotation> containerType, Processor<T> processor,
                                                Set<AnnotatedElement> visited, int metaDepth) {

        Assert.notNull(element, "AnnotatedElement must not be null");

        if (visited.add(element)) {
            try {
                // Start searching within locally declared annotations
                List<Annotation> declaredAnnotations = Arrays.asList(element.getDeclaredAnnotations());
                T result = searchWithGetSemanticsInAnnotations(element, declaredAnnotations,
                        annotationType, annotationName, containerType, processor, visited, metaDepth);
                if (result != null) {
                    return result;
                }

                if (element instanceof Class) { // otherwise getAnnotations doesn't return anything new
                    List<Annotation> inheritedAnnotations = new ArrayList<>();
                    for (Annotation annotation : element.getAnnotations()) {
                        if (!declaredAnnotations.contains(annotation)) {
                            inheritedAnnotations.add(annotation);
                        }
                    }

                    // Continue searching within inherited annotations
                    result = searchWithGetSemanticsInAnnotations(element, inheritedAnnotations,
                            annotationType, annotationName, containerType, processor, visited, metaDepth);
                    if (result != null) {
                        return result;
                    }
                }
            } catch (Throwable ex) {
            }
        }

        return null;
    }

    private static <T> T searchWithGetSemanticsInAnnotations(AnnotatedElement element,
                                                             List<Annotation> annotations, Class<? extends Annotation> annotationType,
                                                             String annotationName, Class<? extends Annotation> containerType,
                                                             Processor<T> processor, Set<AnnotatedElement> visited, int metaDepth) {

        // Search in annotations
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> currentAnnotationType = annotation.annotationType();
            if (!AnnotationUtils.isInJavaLangAnnotationPackage(currentAnnotationType)) {
                if (currentAnnotationType == annotationType ||
                        currentAnnotationType.getName().equals(annotationName) ||
                        processor.alwaysProcesses()) {
                    T result = processor.process(element, annotation, metaDepth);
                    if (result != null) {
                        if (processor.aggregates() && metaDepth == 0) {
                            processor.getAggregatedResults().add(result);
                        } else {
                            return result;
                        }
                    }
                }
                // Repeatable annotations in container?
                else if (currentAnnotationType == containerType) {
                    for (Annotation contained : getRawAnnotationsFromContainer(element, annotation)) {
                        T result = processor.process(element, contained, metaDepth);
                        if (result != null) {
                            // No need to post-process since repeatable annotations within a
                            // container cannot be composed annotations.
                            processor.getAggregatedResults().add(result);
                        }
                    }
                }
            }
        }

        // Recursively search in meta-annotations
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> currentAnnotationType = annotation.annotationType();
            if (!AnnotationUtils.isInJavaLangAnnotationPackage(currentAnnotationType)) {
                T result = searchWithGetSemantics(currentAnnotationType, annotationType,
                        annotationName, containerType, processor, visited, metaDepth + 1);
                if (result != null) {
                    processor.postProcess(element, annotation, result);
                    if (processor.aggregates() && metaDepth == 0) {
                        processor.getAggregatedResults().add(result);
                    } else {
                        return result;
                    }
                }
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private static <A extends Annotation> A[] getRawAnnotationsFromContainer(
            AnnotatedElement element, Annotation container) {

        try {
            A[] value = (A[]) AnnotationUtils.getValue(container);
            if (value != null) {
                return value;
            }
        } catch (Throwable ex) {
        }
        // Unable to read value from repeating annotation container -> ignore it.
        return (A[]) EMPTY_ANNOTATION_ARRAY;
    }

    private interface Processor<T> {

        T process(AnnotatedElement annotatedElement, Annotation annotation, int metaDepth);

        void postProcess(AnnotatedElement annotatedElement, Annotation annotation, T result);

        boolean alwaysProcesses();

        boolean aggregates();

        List<T> getAggregatedResults();
    }

    private static class MergedAnnotationAttributesProcessor implements Processor<AnnotationAttributes> {

        private final boolean classValuesAsString;

        private final boolean nestedAnnotationsAsMap;

        private final boolean aggregates;

        private final List<AnnotationAttributes> aggregatedResults;

        MergedAnnotationAttributesProcessor() {
            this(false, false, false);
        }

        MergedAnnotationAttributesProcessor(boolean classValuesAsString, boolean nestedAnnotationsAsMap) {
            this(classValuesAsString, nestedAnnotationsAsMap, false);
        }

        MergedAnnotationAttributesProcessor(boolean classValuesAsString, boolean nestedAnnotationsAsMap,
                                            boolean aggregates) {

            this.classValuesAsString = classValuesAsString;
            this.nestedAnnotationsAsMap = nestedAnnotationsAsMap;
            this.aggregates = aggregates;
            this.aggregatedResults = (aggregates ? new ArrayList<>() : Collections.emptyList());
        }

        @Override
        public boolean alwaysProcesses() {
            return false;
        }

        @Override
        public boolean aggregates() {
            return this.aggregates;
        }

        @Override
        public List<AnnotationAttributes> getAggregatedResults() {
            return this.aggregatedResults;
        }

        @Override
        public AnnotationAttributes process(AnnotatedElement annotatedElement, Annotation annotation, int metaDepth) {
            return AnnotationUtils.retrieveAnnotationAttributes(annotatedElement, annotation,
                    this.classValuesAsString, this.nestedAnnotationsAsMap);
        }

        @Override
        public void postProcess(AnnotatedElement element, Annotation annotation, AnnotationAttributes attributes) {
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
                    if (aliases != null) {
                        for (String alias : aliases) {
                            if (!valuesAlreadyReplaced.contains(alias)) {
                                targetAttributeNames.add(alias);
                                valuesAlreadyReplaced.add(alias);
                            }
                        }
                    }

                    overrideAttributes(element, annotation, attributes, attributeName, targetAttributeNames);
                }
                // Implicit annotation attribute override based on convention
                else if (!AnnotationUtils.VALUE.equals(attributeName) && attributes.containsKey(attributeName)) {
                    overrideAttribute(element, annotation, attributes, attributeName, attributeName);
                }
            }
        }

        private void overrideAttributes(AnnotatedElement element, Annotation annotation,
                                        AnnotationAttributes attributes, String sourceAttributeName, List<String> targetAttributeNames) {

            Object adaptedValue = getAdaptedValue(element, annotation, sourceAttributeName);

            for (String targetAttributeName : targetAttributeNames) {
                attributes.put(targetAttributeName, adaptedValue);
            }
        }

        private void overrideAttribute(AnnotatedElement element, Annotation annotation,
                                       AnnotationAttributes attributes, String sourceAttributeName, String targetAttributeName) {

            attributes.put(targetAttributeName, getAdaptedValue(element, annotation, sourceAttributeName));
        }

        private Object getAdaptedValue(
                AnnotatedElement element, Annotation annotation, String sourceAttributeName) {

            Object value = AnnotationUtils.getValue(annotation, sourceAttributeName);
            return AnnotationUtils.adaptValue(element, value, this.classValuesAsString, this.nestedAnnotationsAsMap);
        }
    }
}
