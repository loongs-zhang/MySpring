package com.dragon.springframework.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author meilin.huang
 * @version 1.0
 * @date 2019-03-27 10:04 AM
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
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getAnnotationMemberValues(Object annotation) throws Exception {
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
        Field values = invocationHandler.getClass().getDeclaredField("memberValues");
        values.setAccessible(true);
        return (Map<String, Object>) values.get(invocationHandler);
    }

    /**
     * 调用无参数方法
     *
     * @param method 方法对象
     * @param target 调用对象
     * @return 执行结果
     */
    public static Object invokeMethod(Method method, Object target) {
        return invokeMethod(method, target, new Object[0]);
    }

    /**
     * 调用指定对象的方法
     *
     * @param method 方法对象
     * @param target 调用对象
     * @param args   方法参数
     * @return 执行结果
     */
    public static Object invokeMethod(Method method, Object target, Object... args) {
        try {
            method.setAccessible(true);
            return method.invoke(target, args);
        } catch (Exception ex) {
            throw new IllegalStateException(String.format("执行%s.%s()方法错误!"
                    , target.getClass().getName(), method.getName()), ex);
        }
    }

    /**
     * 是否为equals方法
     *
     * @see Object#equals(Object)
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
     *
     * @see Object#hashCode()
     */
    public static boolean isHashCodeMethod(Method method) {
        return "hashCode".equals(method.getName()) && method.getParameterCount() == 0;
    }

    /**
     * 是否为Object的toString方法
     *
     * @see Object#toString()
     */
    public static boolean isToStringMethod(Method method) {
        return "toString".equals(method.getName()) && method.getParameterCount() == 0;
    }

    private static final Method[] NO_METHODS = {};

    private static final Field[] NO_FIELDS = {};

    /**
     * Cache for {@link Class#getDeclaredMethods()} plus equivalent default methods
     * from Java 8 based interfaces, allowing for fast iteration.
     */
    private static final Map<Class<?>, Method[]> declaredMethodsCache = new ConcurrentHashMap<>(256);

    /**
     * Cache for {@link Class#getDeclaredFields()}, allowing for fast iteration.
     */
    private static final Map<Class<?>, Field[]> declaredFieldsCache = new ConcurrentHashMap<>(256);

    /**
     * Attempt to find a {@link Field field} on the supplied {@link Class} with the
     * supplied {@code name}. Searches all superclasses up to {@link Object}.
     *
     * @param clazz the class to introspect
     * @param name  the name of the field
     * @return the corresponding Field object, or {@code null} if not found
     */
    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, null);
    }

    /**
     * Attempt to find a {@link Field field} on the supplied {@link Class} with the
     * supplied {@code name} and/or {@link Class type}. Searches all superclasses
     * up to {@link Object}.
     *
     * @param clazz the class to introspect
     * @param name  the name of the field (may be {@code null} if type is specified)
     * @param type  the type of the field (may be {@code null} if name is specified)
     * @return the corresponding Field object, or {@code null} if not found
     */
    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.isTrue(name != null || type != null, "Either name or type of the field must be specified");
        Class<?> searchType = clazz;
        while (Object.class != searchType && searchType != null) {
            Field[] fields = getDeclaredFields(searchType);
            for (Field field : fields) {
                if ((name == null || name.equals(field.getName())) &&
                        (type == null || type.equals(field.getType()))) {
                    return field;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /**
     * Attempt to find a {@link Method} on the supplied class with the supplied name
     * and no parameters. Searches all superclasses up to {@code Object}.
     * <p>Returns {@code null} if no {@link Method} can be found.
     *
     * @param clazz the class to introspect
     * @param name  the name of the method
     * @return the Method object, or {@code null} if none found
     */
    public static Method findMethod(Class<?> clazz, String name) {
        return findMethod(clazz, name, new Class<?>[0]);
    }

    /**
     * Attempt to find a {@link Method} on the supplied class with the supplied name
     * and parameter types. Searches all superclasses up to {@code Object}.
     * <p>Returns {@code null} if no {@link Method} can be found.
     *
     * @param clazz      the class to introspect
     * @param name       the name of the method
     * @param paramTypes the parameter types of the method
     *                   (may be {@code null} to indicate any signature)
     * @return the Method object, or {@code null} if none found
     */
    public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.notNull(name, "Method name must not be null");
        Class<?> searchType = clazz;
        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() : getDeclaredMethods(searchType));
            for (Method method : methods) {
                if (name.equals(method.getName()) &&
                        (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

    /**
     * Make the given method accessible, explicitly setting it accessible if
     * necessary. The {@code setAccessible(true)} method is only called
     * when actually necessary, to avoid unnecessary conflicts with a JVM
     * SecurityManager (if active).
     *
     * @param method the method to make accessible
     * @see Method#setAccessible
     */
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) ||
                !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    /**
     * This variant retrieves {@link Class#getDeclaredMethods()} from a local cache
     * in order to avoid the JVM's SecurityManager check and defensive array copying.
     * In addition, it also includes Java 8 default methods from locally implemented
     * interfaces, since those are effectively to be treated just like declared methods.
     *
     * @param clazz the class to introspect
     * @return the cached array of methods
     * @throws IllegalStateException if introspection fails
     * @see Class#getDeclaredMethods()
     */
    private static Method[] getDeclaredMethods(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        Method[] result = declaredMethodsCache.get(clazz);
        if (result == null) {
            try {
                Method[] declaredMethods = clazz.getDeclaredMethods();
                List<Method> defaultMethods = findConcreteMethodsOnInterfaces(clazz);
                if (defaultMethods != null) {
                    result = new Method[declaredMethods.length + defaultMethods.size()];
                    System.arraycopy(declaredMethods, 0, result, 0, declaredMethods.length);
                    int index = declaredMethods.length;
                    for (Method defaultMethod : defaultMethods) {
                        result[index] = defaultMethod;
                        index++;
                    }
                } else {
                    result = declaredMethods;
                }
                declaredMethodsCache.put(clazz, (result.length == 0 ? NO_METHODS : result));
            } catch (Throwable ex) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() +
                        "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
            }
        }
        return result;
    }

    private static List<Method> findConcreteMethodsOnInterfaces(Class<?> clazz) {
        List<Method> result = null;
        for (Class<?> ifc : clazz.getInterfaces()) {
            for (Method ifcMethod : ifc.getMethods()) {
                if (!Modifier.isAbstract(ifcMethod.getModifiers())) {
                    if (result == null) {
                        result = new LinkedList<>();
                    }
                    result.add(ifcMethod);
                }
            }
        }
        return result;
    }

    /**
     * This variant retrieves {@link Class#getDeclaredFields()} from a local cache
     * in order to avoid the JVM's SecurityManager check and defensive array copying.
     *
     * @param clazz the class to introspect
     * @return the cached array of fields
     * @throws IllegalStateException if introspection fails
     * @see Class#getDeclaredFields()
     */
    private static Field[] getDeclaredFields(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        Field[] result = declaredFieldsCache.get(clazz);
        if (result == null) {
            try {
                result = clazz.getDeclaredFields();
                declaredFieldsCache.put(clazz, (result.length == 0 ? NO_FIELDS : result));
            } catch (Throwable ex) {
                throw new IllegalStateException("Failed to introspect Class [" + clazz.getName() +
                        "] from ClassLoader [" + clazz.getClassLoader() + "]", ex);
            }
        }
        return result;
    }
}
