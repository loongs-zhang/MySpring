package com.example.design.proxy.mydynamicproxy.cglib;

import com.example.design.proxy.mydynamicproxy.ProxyHelper;
import com.example.design.proxy.mydynamicproxy.jdk.InvocationHandler;
import com.example.design.proxy.mydynamicproxy.jdk.Proxy;
import com.example.design.proxy.mydynamicproxy.source.CodeFile;

import java.lang.reflect.Constructor;

/**
 * @author SuccessZhang
 * @date 2020/05/13
 */
@SuppressWarnings("unused")
public class Enhancer {

    /**
     * 手写版cglib动态代理实现
     *
     * @see ProxyHelper#getProxyClass(CodeFile)
     * @see Proxy#newProxyInstance(ClassLoader, Class[], InvocationHandler)
     */
    public static Object create(Class type, MethodInterceptor callback) {
        try {
            //1.生成java源代码文件
            CodeFile codeSource = new CodeFile(type);
            Class<?> proxyClass = ProxyHelper.getProxyClass(codeSource);
            if (proxyClass == null) {
                return null;
            }
            //7.创建代理对象实例
            Constructor<?> constructor = proxyClass.getConstructor(MethodInterceptor.class);
            return constructor.newInstance(callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
