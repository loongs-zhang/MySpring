package com.dragon.springframework.core.proxy.cglib;

import com.dragon.springframework.core.proxy.ProxyHelper;
import com.dragon.springframework.core.proxy.jdk.InvocationHandler;
import com.dragon.springframework.core.proxy.jdk.Proxy;
import com.dragon.springframework.core.proxy.source.CodeFile;

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
     * @param type 请确保传入的type被public修饰
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
