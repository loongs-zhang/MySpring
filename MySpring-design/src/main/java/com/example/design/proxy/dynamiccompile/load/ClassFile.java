package com.example.design.proxy.dynamiccompile.load;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * @author SuccessZhang
 * @date 2020/05/17
 */
public class ClassFile extends SimpleJavaFileObject {

    /**
     * 用于保存字节码
     */
    private ByteArrayOutputStream outputStream;

    public ClassFile(String name) {
        super(URI.create(name + Kind.CLASS.extension), Kind.CLASS);
        this.outputStream = new ByteArrayOutputStream();
    }

    @Override
    public OutputStream openOutputStream() {
        return this.outputStream;
    }

    public byte[] getBytes() {
        return this.outputStream.toByteArray();
    }
}
