package com.example.design.proxy.dynamiccompile.load;

import com.example.design.proxy.dynamiccompile.DynamicCompileDemo;

/**
 * @author SuccessZhang
 * @date 2020/05/17
 */
public class MyClassLoader extends ClassLoader {

    private ClassFile classFile;

    public MyClassLoader(ClassFile classFile) {
        this.classFile = classFile;
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] bytes = this.classFile.getBytes();
        return defineClass(
                DynamicCompileDemo.class.getPackage().getName() + "." + name,
                bytes, 0, bytes.length);
    }
}