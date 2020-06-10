package com.dragon.springframework.core.annotation;

import com.dragon.springframework.core.Assert;
import com.dragon.springframework.core.ObjectUtils;
import com.dragon.springframework.core.ReflectionUtils;
import com.dragon.springframework.core.StringUtils;
import com.dragon.springframework.core.proxy.jdk.InvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link InvocationHandler} for an {@link Annotation} that Spring has
 * <em>synthesized</em> (i.e., wrapped in a dynamic proxy) with additional
 * functionality.
 *
 * @author Sam Brannen
 * @since 4.2
 */
class SynthesizedAnnotationInvocationHandler implements InvocationHandler {

    private final AnnotationAttributeExtractor<?> attributeExtractor;

    private final Map<String, Object> valueCache = new ConcurrentHashMap<>(8);

    SynthesizedAnnotationInvocationHandler(AnnotationAttributeExtractor<?> attributeExtractor) {
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
            value = this.attributeExtractor.getAttributeValue(attributeMethod);
            if (value == null) {
                String msg = String.format("%s returned null for attribute name [%s] from attribute source [%s]",
                        this.attributeExtractor.getClass().getName(), attributeName, this.attributeExtractor.getSource());
                throw new IllegalStateException(msg);
            }
            if (value instanceof Annotation) {
                value = AnnotationUtils.synthesizeAnnotation((Annotation) value, this.attributeExtractor.getAnnotatedElement());
            } else if (value instanceof Annotation[]) {
                value = AnnotationUtils.synthesizeAnnotationArray((Annotation[]) value, this.attributeExtractor.getAnnotatedElement());
            }
            this.valueCache.put(attributeName, value);
        }
        if (value.getClass().isArray()) {
            value = cloneArray(value);
        }
        return value;
    }

    /**
     * Clone the provided array, ensuring that original
     * component type is retained.
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
     * See {@link Annotation#equals(Object)} for a
     * definition of the required algorithm.
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
     * See {@link Annotation#hashCode()} for a
     * definition of the required algorithm.
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

    /**
     * WARNING: we can NOT use any of the {@code nullSafeHashCode()} methods
     * in Spring's {@link ObjectUtils} because those hash code generation
     * algorithms do not comply with the requirements specified in
     * {@link Annotation#hashCode()}.
     */
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
     * See {@link Annotation#toString()} for guidelines on the recommended format.
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
