package com.example.design.proxy.dynamiccompile.compile;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

/**
 * @author SuccessZhang
 * @date 2020/05/17
 */
public class JavaFile extends SimpleJavaFileObject {

    /**
     * 用于保存源代码
     */
    private String content;

    public JavaFile(String name, String content) {
        super(URI.create(name + Kind.SOURCE.extension), Kind.SOURCE);
        this.content = content;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return this.content;
    }

}
