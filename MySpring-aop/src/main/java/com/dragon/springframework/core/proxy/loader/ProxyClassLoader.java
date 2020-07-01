package com.dragon.springframework.core.proxy.loader;

import com.dragon.springframework.core.proxy.source.ClassFile;

/**
 * 自定义类加载器
 *
 * @author SuccessZhang
 * @date 2020/05/13
 */
public class ProxyClassLoader extends ClassLoader {

    private ClassFile classFile;

    public ProxyClassLoader(ClassFile classFile) {
        this.classFile = classFile;
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] bytes = this.classFile.getBytes();
        //定义的class和ProxyClassLoader在相同的包下
        return defineClass(
                ProxyClassLoader.class.getPackage().getName() + "." + name,
                bytes, 0, bytes.length);
    }
}