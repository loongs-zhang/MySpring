package com.dragon.springframework.core.annotation;

import com.dragon.springframework.core.Assert;
import com.dragon.springframework.core.ObjectUtils;
import com.dragon.springframework.core.ReflectionUtils;
import com.dragon.springframework.core.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 组合注解代理处理器。
 *
 * @author SuccessZhang
 * @date 2020/06/10
 */
@SuppressWarnings("all")
class SynthesizedAnnotationInvocationHandler implements InvocationHandler {

    private final AnnotationAttributeExtractor attributeExtractor;

    private final Map<String, Object> valueCache = new ConcurrentHashMap<>(8);

    SynthesizedAnnotationInvocationHandler(AnnotationAttributeExtractor attributeExtractor) {
        Assert.notNull(attributeExtractor, "AnnotationAttributeExtractor must not be null");
        this.attributeExtractor = attributeExtractor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (ReflectionUtils.isEqualsMethod(method)) {
            return annotationEquals(args[0]);
        }
        if (ReflectionUtils.isHashCodeMethod(method)) {
            return annotationHashCode();
        }
        if (ReflectionUtils.isToStringMethod(method)) {
            return annotationToString();
        }
        if (AnnotationUtils.isAnnotationTypeMethod(method)) {
            return annotationType();
        }
        if (AnnotationUtils.isAttributeMethod(method)) {
            return getAttributeValue(method);
        }
        throw new RuntimeException(String.format(
                "Method [%s] is unsupported for synthesized annotation type [%s]", method, annotationType()));
    }

    private Class<? extends Annotation> annotationType() {
        return this.attributeExtractor.getAnnotationType();
    }

    private Object getAttributeValue(Method attributeMethod) {
        String attributeName = attributeMethod.getName();
        Object value = this.valueCache.get(attributeName);
        if (value == null) {
            value = this.attributeExtractor.getAttributeValue(attributeName);
            if (value == null) {
                String msg = String.format("%s returned null for attribute name [%s]",
                        this.attributeExtractor.getClass().getName(), attributeName);
                throw new IllegalStateException(msg);
            }
            if (value instanceof Annotation) {
                value = AnnotationUtils.getMergedAnnotation(this.attributeExtractor.getAnnotatedElement(), ((Annotation) value).annotationType());
            } else if (value instanceof Annotation[]) {
                value = AnnotationUtils.synthesizeAnnotationArray(this.attributeExtractor.getAnnotatedElement(), (Annotation[]) value);
            }
            this.valueCache.put(attributeName, value);
        }
        if (value != null && value.getClass().isArray()) {
            value = cloneArray(value);
        }
        return value;
    }

    /**
     * 克隆提供的数组，确保保留原始组件类型。
     */
    private Object cloneArray(Object array) {
        if (array instanceof boolean[]) {
            return ((boolean[]) array).clone();
        }
        if (array instanceof byte[]) {
            return ((byte[]) array).clone();
        }
        if (array instanceof char[]) {
            return ((char[]) array).clone();
        }
        if (array instanceof double[]) {
            return ((double[]) array).clone();
        }
        if (array instanceof float[]) {
            return ((float[]) array).clone();
        }
        if (array instanceof int[]) {
            return ((int[]) array).clone();
        }
        if (array instanceof long[]) {
            return ((long[]) array).clone();
        }
        if (array instanceof short[]) {
            return ((short[]) array).clone();
        }
        return ((Object[]) array).clone();
    }

    /**
     * 代理组合注解equals方法
     * {@link Annotation#equals(Object)}
     */
    private boolean annotationEquals(Object other) {
        if (this == other) {
            return true;
        }
        if (!annotationType().isInstance(other)) {
            return false;
        }
        for (Method attributeMethod : AnnotationUtils.getAttributeMethods(annotationType())) {
            Object thisValue = getAttributeValue(attributeMethod);
            Object otherValue = ReflectionUtils.invokeMethod(attributeMethod, other);
            if (!ObjectUtils.nullSafeEquals(thisValue, otherValue)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 代理组合注解hashCode方法
     * {@link Annotation#hashCode()}
     */
    private int annotationHashCode() {
        int result = 0;
        for (Method attributeMethod : AnnotationUtils.getAttributeMethods(annotationType())) {
            Object value = getAttributeValue(attributeMethod);
            int hashCode;
            if (value.getClass().isArray()) {
                hashCode = hashCodeForArray(value);
            } else {
                hashCode = value.hashCode();
            }
            result += (127 * attributeMethod.getName().hashCode()) ^ hashCode;
        }
        return result;
    }

    private int hashCodeForArray(Object array) {
        if (array instanceof boolean[]) {
            return Arrays.hashCode((boolean[]) array);
        }
        if (array instanceof byte[]) {
            return Arrays.hashCode((byte[]) array);
        }
        if (array instanceof char[]) {
            return Arrays.hashCode((char[]) array);
        }
        if (array instanceof double[]) {
            return Arrays.hashCode((double[]) array);
        }
        if (array instanceof float[]) {
            return Arrays.hashCode((float[]) array);
        }
        if (array instanceof int[]) {
            return Arrays.hashCode((int[]) array);
        }
        if (array instanceof long[]) {
            return Arrays.hashCode((long[]) array);
        }
        if (array instanceof short[]) {
            return Arrays.hashCode((short[]) array);
        }
        return Arrays.hashCode((Object[]) array);
    }

    /**
     * 代理组合注解toString方法
     * {@link Annotation#toString()}
     */
    private String annotationToString() {
        StringBuilder sb = new StringBuilder("@").append(annotationType().getName()).append("(");
        Iterator<Method> iterator = AnnotationUtils.getAttributeMethods(annotationType()).iterator();
        while (iterator.hasNext()) {
            Method attributeMethod = iterator.next();
            sb.append(attributeMethod.getName());
            sb.append('=');
            sb.append(attributeValueToString(getAttributeValue(attributeMethod)));
            sb.append(iterator.hasNext() ? ", " : "");
        }
        return sb.append(")").toString();
    }

    private String attributeValueToString(Object value) {
        if (value instanceof Object[]) {
            return "[" + StringUtils.arrayToDelimitedString((Object[]) value, ", ") + "]";
        }
        return String.valueOf(value);
    }

}
