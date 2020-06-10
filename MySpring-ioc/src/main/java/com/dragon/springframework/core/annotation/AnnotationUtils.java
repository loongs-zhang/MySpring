package com.dragon.springframework.core.annotation;

import com.dragon.springframework.core.Assert;
import com.dragon.springframework.core.ObjectUtils;
import com.dragon.springframework.core.ReflectionUtils;
import com.dragon.springframework.core.StringUtils;
import com.dragon.springframework.core.proxy.jdk.InvocationHandler;
import com.dragon.springframework.core.proxy.jdk.Proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("all")
public class AnnotationUtils {

    private static final Map<Class<? extends Annotation>, Boolean> synthesizableCache =
            new ConcurrentHashMap<>(256);

    private static final Map<Class<? extends Annotation>, Map<String, List<String>>> attributeAliasesCache =
            new ConcurrentHashMap<>(256);

    private static final Map<Class<? extends Annotation>, List<Method>> attributeMethodsCache =
            new ConcurrentHashMap<>(256);

    @SuppressWarnings("unchecked")
    public static <A extends Annotation> A synthesizeAnnotation(A annotation, Object annotatedElement) {
        Assert.notNull(annotation, "Annotation must not be null");
        if (annotation instanceof SynthesizedAnnotation) {
            return annotation;
        }
        Class<? extends Annotation> annotationType = annotation.annotationType();
        if (!isSynthesizable(annotationType)) {
            return annotation;
        }
        DefaultAnnotationAttributeExtractor attributeExtractor =
                new DefaultAnnotationAttributeExtractor(annotation, annotatedElement);
        InvocationHandler handler = new SynthesizedAnnotationInvocationHandler(attributeExtractor);
        Class<?>[] exposedInterfaces = new Class<?>[]{annotationType, SynthesizedAnnotation.class};
        return (A) Proxy.newProxyInstance(annotation.getClass().getClassLoader(), exposedInterfaces, handler);
    }

    @SuppressWarnings("unchecked")
    public static boolean isSynthesizable(Class<? extends Annotation> annotationType) {
        Boolean synthesizable = synthesizableCache.get(annotationType);
        if (synthesizable == null) {
            synthesizable = Boolean.FALSE;
            for (Method attribute : getAttributeMethods(annotationType)) {
                if (!getAttributeAliasNames(attribute).isEmpty()) {
                    synthesizable = Boolean.TRUE;
                    break;
                }
                Class<?> returnType = attribute.getReturnType();
                if (Annotation[].class.isAssignableFrom(returnType)) {
                    Class<? extends Annotation> nestedAnnotationType =
                            (Class<? extends Annotation>) returnType.getComponentType();
                    if (isSynthesizable(nestedAnnotationType)) {
                        synthesizable = Boolean.TRUE;
                        break;
                    }
                } else if (Annotation.class.isAssignableFrom(returnType)) {
                    Class<? extends Annotation> nestedAnnotationType = (Class<? extends Annotation>) returnType;
                    if (isSynthesizable(nestedAnnotationType)) {
                        synthesizable = Boolean.TRUE;
                        break;
                    }
                }
            }
            synthesizableCache.put(annotationType, synthesizable);
        }
        return synthesizable;
    }

    public static List<String> getAttributeAliasNames(Method attribute) {
        Assert.notNull(attribute, "attribute must not be null");
        AliasDescriptor descriptor = AliasDescriptor.from(attribute);
        return (descriptor != null ? descriptor.getAttributeAliasNames() : Collections.emptyList());
    }

    @SuppressWarnings("unchecked")
    public static <A extends Annotation> A synthesizeAnnotation(Map<String, Object> attributes,
                                                                Class<A> annotationType,
                                                                AnnotatedElement annotatedElement) {
        Assert.notNull(attributes, "'attributes' must not be null");
        Assert.notNull(annotationType, "'annotationType' must not be null");
        MapAnnotationAttributeExtractor attributeExtractor =
                new MapAnnotationAttributeExtractor(attributes, annotationType, annotatedElement);
        InvocationHandler handler = new SynthesizedAnnotationInvocationHandler(attributeExtractor);
        Class<?>[] exposedInterfaces = new Class<?>[]{annotationType, SynthesizedAnnotation.class};
        return (A) Proxy.newProxyInstance(annotationType.getClassLoader(), exposedInterfaces, handler);
    }

    static void postProcessAnnotationAttributes(Object annotatedElement,
                                                AnnotationAttributes attributes,
                                                boolean classValuesAsString,
                                                boolean nestedAnnotationsAsMap) {
        if (attributes == null) {
            return;
        }
        Class<? extends Annotation> annotationType = attributes.annotationType();
        Set<String> valuesAlreadyReplaced = new HashSet<>();
        Map<String, List<String>> aliasMap = getAttributeAliasMap(annotationType);
        for (String attributeName : aliasMap.keySet()) {
            if (valuesAlreadyReplaced.contains(attributeName)) {
                continue;
            }
            Object value = attributes.get(attributeName);
            boolean valuePresent = (value != null && !(value instanceof DefaultValueHolder));
            for (String aliasedAttributeName : aliasMap.get(attributeName)) {
                if (valuesAlreadyReplaced.contains(aliasedAttributeName)) {
                    continue;
                }
                Object aliasedValue = attributes.get(aliasedAttributeName);
                boolean aliasPresent = (aliasedValue != null && !(aliasedValue instanceof DefaultValueHolder));
                if (valuePresent && aliasPresent) {
                    if (!ObjectUtils.nullSafeEquals(value, aliasedValue)) {
                        String elementAsString =
                                (annotatedElement != null ? annotatedElement.toString() : "unknown element");
                        throw new RuntimeException(String.format(
                                "In AnnotationAttributes for annotation [%s] declared on %s, " +
                                        "attribute '%s' and its alias '%s' are declared with values of [%s] and [%s], " +
                                        "but only one is permitted.", annotationType.getName(), elementAsString,
                                attributeName, aliasedAttributeName, ObjectUtils.nullSafeToString(value),
                                ObjectUtils.nullSafeToString(aliasedValue)));
                    }
                } else if (aliasPresent) {
                    attributes.put(attributeName,
                            adaptValue(annotatedElement, aliasedValue, classValuesAsString, nestedAnnotationsAsMap));
                    valuesAlreadyReplaced.add(attributeName);
                } else if (valuePresent) {
                    attributes.put(aliasedAttributeName,
                            adaptValue(annotatedElement, value, classValuesAsString, nestedAnnotationsAsMap));
                    valuesAlreadyReplaced.add(aliasedAttributeName);
                }
            }
        }
        for (String attributeName : attributes.keySet()) {
            if (valuesAlreadyReplaced.contains(attributeName)) {
                continue;
            }
            Object value = attributes.get(attributeName);
            if (value instanceof DefaultValueHolder) {
                value = ((DefaultValueHolder) value).getDefaultValue();
                attributes.put(attributeName,
                        adaptValue(annotatedElement, value, classValuesAsString, nestedAnnotationsAsMap));
            }
        }
    }

    private static Object adaptValue(Object annotatedElement, Object value,
                                     boolean classValuesAsString,
                                     boolean nestedAnnotationsAsMap) {
        if (classValuesAsString) {
            if (value instanceof Class) {
                return ((Class<?>) value).getName();
            } else if (value instanceof Class[]) {
                Class<?>[] clazzArray = (Class<?>[]) value;
                String[] classNames = new String[clazzArray.length];
                for (int i = 0; i < clazzArray.length; i++) {
                    classNames[i] = clazzArray[i].getName();
                }
                return classNames;
            }
        }
        if (value instanceof Annotation) {
            Annotation annotation = (Annotation) value;
            if (nestedAnnotationsAsMap) {
                return getAnnotationAttributes(annotatedElement, annotation, classValuesAsString, true);
            } else {
                return synthesizeAnnotation(annotation, annotatedElement);
            }
        }
        if (value instanceof Annotation[]) {
            Annotation[] annotations = (Annotation[]) value;
            if (nestedAnnotationsAsMap) {
                AnnotationAttributes[] mappedAnnotations = new AnnotationAttributes[annotations.length];
                for (int i = 0; i < annotations.length; i++) {
                    mappedAnnotations[i] =
                            getAnnotationAttributes(annotatedElement, annotations[i], classValuesAsString, true);
                }
                return mappedAnnotations;
            } else {
                return synthesizeAnnotationArray(annotations, annotatedElement);
            }
        }
        return value;
    }

    static Object getAdaptedValue(
            AnnotatedElement element, Annotation annotation, String sourceAttributeName) {
        Object value = getValue(annotation, sourceAttributeName);
        return adaptValue(element, value, false, false);
    }

    static Annotation[] synthesizeAnnotationArray(
            Annotation[] annotations, Object annotatedElement) {
        Annotation[] synthesized = (Annotation[]) Array.newInstance(
                annotations.getClass().getComponentType(), annotations.length);
        for (int i = 0; i < annotations.length; i++) {
            synthesized[i] = synthesizeAnnotation(annotations[i], annotatedElement);
        }
        return synthesized;
    }

    @SuppressWarnings("unchecked")
    static <A extends Annotation> A[] synthesizeAnnotationArray(Map<String, Object>[] maps, Class<A> annotationType) {
        Assert.notNull(annotationType, "'annotationType' must not be null");
        if (maps == null) {
            return null;
        }
        A[] synthesized = (A[]) Array.newInstance(annotationType, maps.length);
        for (int i = 0; i < maps.length; i++) {
            synthesized[i] = synthesizeAnnotation(maps[i], annotationType, null);
        }
        return synthesized;
    }

    public static AnnotationAttributes getAnnotationAttributes(Object annotatedElement,
                                                               Annotation annotation,
                                                               boolean classValuesAsString,
                                                               boolean nestedAnnotationsAsMap) {
        AnnotationAttributes attributes =
                retrieveAnnotationAttributes(annotatedElement, annotation, classValuesAsString, nestedAnnotationsAsMap);
        postProcessAnnotationAttributes(annotatedElement, attributes, classValuesAsString, nestedAnnotationsAsMap);
        return attributes;
    }

    static AnnotationAttributes retrieveAnnotationAttributes(Object annotatedElement, Annotation annotation,
                                                             boolean classValuesAsString, boolean nestedAnnotationsAsMap) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        AnnotationAttributes attributes = new AnnotationAttributes(annotationType);
        for (Method method : getAttributeMethods(annotationType)) {
            try {
                Object attributeValue = method.invoke(annotation);
                Object defaultValue = method.getDefaultValue();
                if (defaultValue != null && ObjectUtils.nullSafeEquals(attributeValue, defaultValue)) {
                    attributeValue = new DefaultValueHolder(defaultValue);
                }
                attributes.put(method.getName(),
                        adaptValue(annotatedElement, attributeValue, classValuesAsString, nestedAnnotationsAsMap));
            } catch (Throwable ex) {
                throw new IllegalStateException("Could not obtain annotation attribute value for " + method, ex);
            }
        }
        return attributes;
    }

    public static List<Method> getAttributeMethods(Class<? extends Annotation> annotationType) {
        List<Method> methods = attributeMethodsCache.get(annotationType);
        if (methods == null) {
            methods = new ArrayList<>();
            for (Method method : annotationType.getDeclaredMethods()) {
                if (isAttributeMethod(method)) {
                    ReflectionUtils.makeAccessible(method);
                    methods.add(method);
                }
            }
            attributeMethodsCache.put(annotationType, methods);
        }
        return methods;
    }

    public static boolean isAttributeMethod(Method method) {
        return (method != null && method.getParameterCount() == 0 && method.getReturnType() != void.class);
    }

    public static boolean isAnnotationTypeMethod(Method method) {
        return (method != null && "annotationType".equals(method.getName()) && method.getParameterCount() == 0);
    }

    public static String getAttributeOverrideName(Method attribute, Class<? extends Annotation> metaAnnotationType) {
        Assert.notNull(attribute, "attribute must not be null");
        Assert.notNull(metaAnnotationType, "metaAnnotationType must not be null");
        Assert.isTrue(Annotation.class != metaAnnotationType,
                "metaAnnotationType must not be [java.lang.annotation.Annotation]");
        AliasDescriptor descriptor = AliasDescriptor.from(attribute);
        return (descriptor != null ? descriptor.getAttributeOverrideName(metaAnnotationType) : null);
    }

    public static Map<String, List<String>> getAttributeAliasMap(Class<? extends Annotation> annotationType) {
        if (annotationType == null) {
            return Collections.emptyMap();
        }
        Map<String, List<String>> map = attributeAliasesCache.get(annotationType);
        if (map == null) {
            map = new LinkedHashMap<>();
            for (Method attribute : getAttributeMethods(annotationType)) {
                List<String> aliasNames = getAttributeAliasNames(attribute);
                if (!aliasNames.isEmpty()) {
                    map.put(attribute.getName(), aliasNames);
                }
            }
            attributeAliasesCache.put(annotationType, map);
        }
        return map;
    }

    public static Object getValue(Annotation annotation, String attributeName) {
        if (annotation == null || !StringUtils.hasText(attributeName)) {
            return null;
        }
        try {
            Method method = annotation.annotationType().getDeclaredMethod(attributeName);
            ReflectionUtils.makeAccessible(method);
            return method.invoke(annotation);
        } catch (InvocationTargetException ex) {
            throw new IllegalStateException(
                    "Could not obtain value for annotation attribute '" + attributeName + "' in " + annotation, ex);
        } catch (Throwable ex) {
            return null;
        }
    }

    public static boolean isInJavaLangAnnotationPackage(Class<? extends Annotation> annotationType) {
        return (annotationType != null && annotationType.getName().startsWith("java.lang.annotation"));
    }

    public static Object getDefaultValue(
            Class<? extends Annotation> annotationType, String attributeName) {
        if (annotationType == null || !StringUtils.hasText(attributeName)) {
            return null;
        }
        try {
            return annotationType.getDeclaredMethod(attributeName).getDefaultValue();
        } catch (Throwable ex) {
            return null;
        }
    }

    public static boolean isAnnotationMetaPresent(Class<? extends Annotation> annotationType,
                                                  Class<? extends Annotation> metaAnnotationType) {
        Assert.notNull(annotationType, "Annotation type must not be null");
        if (metaAnnotationType == null) {
            return false;
        }
        Boolean metaPresent = Boolean.FALSE;
        if (findAnnotation(annotationType, metaAnnotationType) != null) {
            metaPresent = Boolean.TRUE;
        }
        return metaPresent;
    }

    public static <A extends Annotation> A findAnnotation(
            Class<?> clazz, Class<A> annotationType) {
        Assert.notNull(clazz, "Class must not be null");
        if (annotationType == null) {
            return null;
        }
        return findAnnotation(clazz, annotationType, new HashSet<>());
    }

    private static <A extends Annotation> A findAnnotation(Class<?> clazz, Class<A> annotationType, Set<Annotation> visited) {
        try {
            A annotation = clazz.getDeclaredAnnotation(annotationType);
            if (annotation != null) {
                return annotation;
            }
            for (Annotation declaredAnn : clazz.getDeclaredAnnotations()) {
                Class<? extends Annotation> declaredType = declaredAnn.annotationType();
                if (!isInJavaLangAnnotationPackage(declaredType) && visited.add(declaredAnn)) {
                    annotation = findAnnotation(declaredType, annotationType, visited);
                    if (annotation != null) {
                        return annotation;
                    }
                }
            }
        } catch (Throwable ex) {
            return null;
        }
        for (Class<?> ifc : clazz.getInterfaces()) {
            A annotation = findAnnotation(ifc, annotationType, visited);
            if (annotation != null) {
                return annotation;
            }
        }
        Class<?> superclass = clazz.getSuperclass();
        if (superclass == null || Object.class == superclass) {
            return null;
        }
        return findAnnotation(superclass, annotationType, visited);
    }
}