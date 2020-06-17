package com.dragon.springframework.core.annotation;

import com.dragon.springframework.core.Assert;
import com.dragon.springframework.core.ReflectionUtils;
import com.dragon.springframework.core.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 此类中的所有返回注解、注解数组或{@link Map}的公共方法
 * 都支持通过{@link AliasFor @AliasFor}配置的属性别名。
 *
 * @author SuccessZhang
 * @date 2020/06/11
 */
public class AnnotationUtils {

    private static final Map<Class<? extends Annotation>, List<Method>> ATTRIBUTE_METHODS_CACHE =
            new ConcurrentHashMap<>(256);

    private static final Map<Class<? extends Annotation>, Map<String, List<String>>> ATTRIBUTE_ALIASES_CACHE =
            new ConcurrentHashMap<>(256);

    /**
     * 获取指定元素上的指定的、合并后的注解。
     */
    public static <A extends Annotation> A getMergedAnnotation(AnnotatedElement element, Class<A> annotationType) {
        Assert.notNull(annotationType, "'annotationType' must not be null");
        // 详尽检索合并的注解属性
        Map<String, Object> attributes = getMergedAnnotationAttributes(element, annotationType);
        if (attributes == null) {
            return null;
        }
        return synthesizeAnnotation(attributes, annotationType, element);
    }

    public static <A extends Annotation> Map<String, Object> getMergedAnnotationAttributes(
            AnnotatedElement element, Class<A> targetAnnotationType) {
        Set<Annotation> visited = new HashSet<>();
        A target = findAnnotation(element, targetAnnotationType, visited);
        if (target == null) {
            return null;
        }
        Map<String, Object> attributes = new LinkedHashMap<>(8);
        for (Annotation annotation : visited) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            List<AliasDescriptor> descriptors = new ArrayList<>();
            for (Method attribute : getAttributeMethods(annotationType)) {
                AliasDescriptor des = AliasDescriptor.from(attribute);
                if (des != null) {
                    descriptors.add(des);
                }
            }
            if (descriptors.isEmpty()) {
                continue;
            }
            // 遍历该注解上的所有别名描述器，并判断目标注解的属性是否有被其他注解覆盖
            for (AliasDescriptor descriptor : descriptors) {
                if (descriptor.getAliasedAnnotationType() == targetAnnotationType) {
                    String aliasedAttributeName = descriptor.getAliasedAttributeName();
                    if (!attributes.containsKey(aliasedAttributeName)) {
                        Object value = ReflectionUtils.invokeMethod(descriptor.getSourceAttribute(),
                                getMergedAnnotation(element, descriptor.getSourceAnnotationType()));
                        attributes.put(aliasedAttributeName, value);
                        handleForEachOtherSituation(attributes, targetAnnotationType, descriptor.getAliasedAttribute(), value);
                    }
                }
            }
        }
        // 判断哪些属性没有被覆盖，没覆盖则补上对应的属性值，需要考虑互为别名的情况
        for (Method attribute : getAttributeMethods(targetAnnotationType)) {
            String attributeName = attribute.getName();
            if (!attributes.containsKey(attributeName)) {
                Object value = ReflectionUtils.invokeMethod(attribute, target);
                attributes.put(attributeName, value);
                Object defaultValue = getDefaultValue(targetAnnotationType, attribute.getName());
                if (value.equals(defaultValue)) {
                    continue;
                }
                handleForEachOtherSituation(attributes, targetAnnotationType, attribute, value);
            }
        }
        return attributes;
    }

    /**
     * 处理同一注解中不同属性互为别名的情况。
     */
    private static void handleForEachOtherSituation(Map<String, Object> attributes, Class<? extends Annotation> targetType, Method attribute, Object value) {
        String attributeOverrideName = getAttributeOverrideName(attribute, targetType);
        if (attributeOverrideName == null) {
            return;
        }
        attributes.put(attributeOverrideName, value);
    }

    @SuppressWarnings("unchecked")
    public static <A extends Annotation> A synthesizeAnnotation(Map<String, Object> attributes,
                                                                Class<A> annotationType,
                                                                AnnotatedElement annotatedElement) {
        Assert.notNull(attributes, "'attributes' must not be null");
        Assert.notNull(annotationType, "'annotationType' must not be null");
        MapAnnotationAttributeExtractor attributeExtractor =
                new MapAnnotationAttributeExtractor(annotationType, annotatedElement, attributes);
        InvocationHandler handler = new SynthesizedAnnotationInvocationHandler(attributeExtractor);
        return (A) Proxy.newProxyInstance(annotationType.getClassLoader(),
                new Class<?>[]{annotationType}, handler);
    }

    static Annotation[] synthesizeAnnotationArray(
            AnnotatedElement annotatedElement, Annotation[] annotations) {
        Annotation[] synthesized = (Annotation[]) Array.newInstance(
                annotations.getClass().getComponentType(), annotations.length);
        for (int i = 0; i < annotations.length; i++) {
            synthesized[i] = getMergedAnnotation(annotatedElement, annotations[i].annotationType());
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

    /**
     * 获取通过{@link AliasFor}配置的覆盖属性的名称。
     */
    public static String getAttributeOverrideName(Method attribute, Class<? extends Annotation> metaAnnotationType) {
        Assert.notNull(attribute, "attribute must not be null");
        Assert.notNull(metaAnnotationType, "metaAnnotationType must not be null");
        Assert.isTrue(Annotation.class != metaAnnotationType,
                "metaAnnotationType must not be [java.lang.annotation.Annotation]");
        AliasDescriptor descriptor = AliasDescriptor.from(attribute);
        return (descriptor != null ? descriptor.getAttributeOverrideName(metaAnnotationType) : null);
    }

    static Map<String, List<String>> getAttributeAliasMap(Class<? extends Annotation> annotationType) {
        if (annotationType == null) {
            return Collections.emptyMap();
        }
        Map<String, List<String>> map = ATTRIBUTE_ALIASES_CACHE.get(annotationType);
        if (map == null) {
            map = new LinkedHashMap<>();
            for (Method attribute : getAttributeMethods(annotationType)) {
                AliasDescriptor descriptor = AliasDescriptor.from(attribute);
                if (descriptor == null) {
                    continue;
                }
                List<String> aliasNames = descriptor.getAttributeAliasNames();
                if (!aliasNames.isEmpty()) {
                    map.put(attribute.getName(), aliasNames);
                }
            }
            ATTRIBUTE_ALIASES_CACHE.put(annotationType, map);
        }
        return map;
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

    public static boolean isAnnotationMetaPresent(AnnotatedElement annotatedElement,
                                                  Class<? extends Annotation> metaAnnotationType) {
        Assert.notNull(annotatedElement, "Annotation type must not be null");
        if (metaAnnotationType == null) {
            return false;
        }
        Boolean metaPresent = Boolean.FALSE;
        if (findAnnotation(annotatedElement, metaAnnotationType) != null) {
            metaPresent = Boolean.TRUE;
        }
        return metaPresent;
    }

    public static <A extends Annotation> A findAnnotation(
            AnnotatedElement annotatedElement, Class<A> annotationType) {
        Assert.notNull(annotatedElement, "Class must not be null");
        if (annotationType == null) {
            return null;
        }
        return findAnnotation(annotatedElement, annotationType, new HashSet<>());
    }

    private static <A extends Annotation> A findAnnotation(AnnotatedElement annotatedElement,
                                                           Class<A> annotationType,
                                                           Set<Annotation> visited) {
        try {
            A annotation = annotatedElement.getDeclaredAnnotation(annotationType);
            if (annotation != null) {
                return annotation;
            }
            for (Annotation declaredAnn : annotatedElement.getDeclaredAnnotations()) {
                Class<? extends Annotation> declaredType = declaredAnn.annotationType();
                if (!isInJavaLangAnnotationPackage(declaredType) && visited.add(declaredAnn)) {
                    annotation = findAnnotation(declaredType, annotationType, visited);
                    if (annotation != null) {
                        return annotation;
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        if (annotatedElement instanceof Class<?>) {
            Class<?> clazz = (Class<?>) annotatedElement;
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
        return null;
    }

    public static List<Method> getAttributeMethods(Class<? extends Annotation> annotationType) {
        List<Method> methods = ATTRIBUTE_METHODS_CACHE.get(annotationType);
        if (methods == null) {
            methods = new ArrayList<>();
            for (Method method : annotationType.getDeclaredMethods()) {
                if (isAttributeMethod(method)) {
                    ReflectionUtils.makeAccessible(method);
                    methods.add(method);
                }
            }
            ATTRIBUTE_METHODS_CACHE.put(annotationType, methods);
        }
        return methods;
    }

    public static boolean isAttributeMethod(Method method) {
        return (method != null && method.getParameterCount() == 0 && method.getReturnType() != void.class);
    }

    public static boolean isAnnotationTypeMethod(Method method) {
        return (method != null && "annotationType".equals(method.getName()) && method.getParameterCount() == 0);
    }
}