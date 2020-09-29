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
     * 返回匹配方法的索引。以后可以使用索引*以较少的开销调用该方法。
     * 如果有多个方法匹配（即它们仅因返回类型而不同），则是任意选择的。
     *
     * @param name           方法名称
     * @param parameterTypes 参数类型数组
     * @return 索引，如果找不到会返回-1
     */
    int getIndex(String name, Class[] parameterTypes);

    /**
     * 用指定的索引调用方法。
     *
     * @param index 方法索引
     * @param obj   调用基础方法的对象
     * @param args  调用方法的参数
     * @return 调用方法后的返回值
     */
    Object invoke(int index, Object obj, Object[] args) throws InvocationTargetException;

}
