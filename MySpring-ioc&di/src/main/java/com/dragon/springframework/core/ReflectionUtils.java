package com.dragon.springframework.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author SuccessZhang
 * @date 2020/06/12
 */
public class ReflectionUtils {

    /**
     * 获取注解的字段属性
     * 如public @interface RedisCache {
     * String key();
     * int expire() default 10 * 60;
     * TimeUnit timeUnit() default TimeUnit.SECONDS;
     * }
     * 获取到的Map包含key：key,expire,timeUnit；
     * 值则是注解所配置的对应key名称方法的值
     *
     * @param annotation 注解实例
     * @return 注解K-V字段Map
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static Map<String, Object> getAnnotationMemberValues(Object annotation) throws Exception {
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
        Field values = invocationHandler.getClass().getDeclaredField("memberValues");
        values.setAccessible(true);
        return (Map<String, Object>) values.get(invocationHandler);
    }

    /**
     * 调用无参数方法。
     */
    public static Object invokeMethod(Method method, Object target) {
        return invokeMethod(method, target, new Object[0]);
    }

    /**
     * 调用指定对象的方法。
     */
    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            makeAccessible(method);
            return method.invoke(target, args);
        } catch (Exception ex) {
            throw new IllegalStateException(String.format("执行%s.%s()方法错误!"
                    , target.getClass().getName(), method.getName()), ex);
        }
    }

    /**
     * 是否为equals方法
     */
    public static boolean isEqualsMethod(Method method) {
        if (!"equals".equals(method.getName())) {
            return false;
        }
        Class<?>[] paramTypes = method.getParameterTypes();
        return (paramTypes.length == 1 && paramTypes[0] == Object.class);
    }

    /**
     * 是否为hashCode方法
     */
    public static boolean isHashCodeMethod(Method method) {
        return "hashCode".equals(method.getName()) && method.getParameterCount() == 0;
    }

    /**
     * 是否为Object的toString方法
     */
    public static boolean isToStringMethod(Method method) {
        return "toString".equals(method.getName()) && method.getParameterCount() == 0;
    }

    /**
     * 必要时显式设置方法为可访问。
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) ||
                !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

}
