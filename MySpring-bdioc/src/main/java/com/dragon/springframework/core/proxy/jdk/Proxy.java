package com.dragon.springframework.core.proxy.jdk;

import com.dragon.springframework.core.proxy.ProxyHelper;
import com.dragon.springframework.core.proxy.cglib.Enhancer;
import com.dragon.springframework.core.proxy.cglib.MethodInterceptor;
import com.dragon.springframework.core.proxy.source.CodeFile;

import java.lang.reflect.Constructor;

/**
 * @author SuccessZhang
 * @date 2020/05/13
 */
@SuppressWarnings("unused")
public class Proxy {

    protected final InvocationHandler h;

    protected Proxy(InvocationHandler h) {
        this.h = h;
    }

    /**
     * 手写版jdk动态代理实现。
     *
     * @param interfaces 请确保传入的接口都被public修饰
     * @see ProxyHelper#getProxyClass(CodeFile)
     * @see Enhancer#create(Class, MethodInterceptor)
     */
    public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) {
        try {
            //1.生成java源代码
            CodeFile codeSource = new CodeFile(interfaces);
            Class<?> proxyClass = ProxyHelper.getProxyClass(codeSource);
            if (proxyClass == null) {
                return null;
            }
            //7.创建代理对象实例
            Constructor<?> constructor = proxyClass.getConstructor(InvocationHandler.class);
            return constructor.newInstance(h);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
