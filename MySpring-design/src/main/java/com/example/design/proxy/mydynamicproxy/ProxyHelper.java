package com.example.design.proxy.mydynamicproxy;

import com.example.design.proxy.mydynamicproxy.loader.ProxyClassLoader;
import com.example.design.proxy.mydynamicproxy.manager.ClassFileManager;
import com.example.design.proxy.mydynamicproxy.source.ClassFile;
import com.example.design.proxy.mydynamicproxy.source.CodeFile;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SuccessZhang
 * @date 2020/05/13
 */
public class ProxyHelper {

    public static final String PROXY_CLASS_PREFIX = "$Proxy";

    private static int proxyClassCount = 0;

    private static final Map<Class<?>, Object> CLASS_INSTANCE_CACHE = new HashMap<>(16);

    private static final Map<Method, Integer> METHOD_INDEX_CACHE = new HashMap<>(64);

    /**
     * @see com.example.design.proxy.mydynamicproxy.manager.ClassFileManager
     */
    public static synchronized Class<?> getProxyClass(CodeFile codeSource) throws ClassNotFoundException {
        try {
            ClassFileManager classFileManager = new ClassFileManager();
            //4.编译java文件，从内存中生成（非写入）class文件
            if (!classFileManager.compile(codeSource)) {
                return null;
            }
            //5.获取字节码容器
            ClassFile classFile = classFileManager.getClassFile();
            //6.装载class文件
            ClassLoader classLoader = new ProxyClassLoader(classFile);
            return classLoader.loadClass(PROXY_CLASS_PREFIX + proxyClassCount);
        } finally {
            proxyClassCount++;
        }
    }

    public static int getProxyClassCount() {
        return proxyClassCount;
    }

    public static Object getClassInstance(Class<?> superClass) {
        return CLASS_INSTANCE_CACHE.get(superClass);
    }

    public static void putClassInstance(Class<?> superClass, Object classInstance) {
        CLASS_INSTANCE_CACHE.put(superClass, classInstance);
    }

    public static Integer getMethodIndex(Method method) {
        return METHOD_INDEX_CACHE.get(method);
    }

    public static void putMethodIndex(Method method, Integer index) {
        METHOD_INDEX_CACHE.put(method, index);
    }
}
