package com.example.design.proxy.mydynamicproxy.cglib;

import com.example.design.proxy.mydynamicproxy.ProxyHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author SuccessZhang
 * @date 2020/05/13
 */
public interface FastClass {

    default Object invoke(Method method, Object[] args) throws
            InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<?> superClass = method.getDeclaringClass();
        Object instance = ProxyHelper.getClassInstance(superClass);
        if (instance == null) {
            instance = superClass.newInstance();
            ProxyHelper.putClassInstance(superClass, instance);
        }
        return invoke(instance, method, args);
    }

    default Object invoke(Object object, Method method, Object[] args) throws
            InvocationTargetException {
        return invoke(getIndexAndCache(method), object, args);
    }

    default Integer getIndexAndCache(Method method) {
        Integer index = ProxyHelper.getMethodIndex(method);
        if (index == null) {
            index = getIndex(method.getName(), method.getParameterTypes());
            ProxyHelper.putMethodIndex(method, index);
        }
        return index;
    }

    /**
     * Return the index of the matching method. The index may be used
     * later to invoke the method with less overhead. If more than one
     * method matches (i.e. they differ by return type only), one is
     * chosen arbitrarily.
     *
     * @param name           the method name
     * @param parameterTypes the parameter array
     * @return the index, or <code>-1</code> if none is found.
     * @see #invoke(int, Object, Object[])
     */
    int getIndex(String name, Class[] parameterTypes);

    /**
     * Invoke the method with the specified index.
     *
     * @param index the method index
     * @param obj   the object the underlying method is invoked from
     * @param args  the arguments used for the method call
     * @throws InvocationTargetException if the underlying method throws an exception
     * @see #getIndex(String, Class[])
     */
    Object invoke(int index, Object obj, Object[] args) throws InvocationTargetException;

}
