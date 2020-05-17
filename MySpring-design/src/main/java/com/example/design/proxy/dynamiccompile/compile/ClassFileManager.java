package com.example.design.proxy.dynamiccompile.compile;

import com.example.design.proxy.dynamiccompile.load.ClassFile;
import lombok.Getter;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.nio.charset.Charset;
import java.util.Collections;

/**
 * @author SuccessZhang
 * @date 2020/05/17
 */
public class ClassFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private static final JavaCompiler COMPILER = ToolProvider.getSystemJavaCompiler();

    @Getter
    private ClassFile classFile;

    public ClassFileManager() {
        super(COMPILER.getStandardFileManager(null, null, Charset.forName("UTF-8")));
    }

    public boolean compile(JavaFile javaFile) {
        JavaCompiler.CompilationTask task = COMPILER.getTask(null, this, null, null, null,
                Collections.singletonList(javaFile));
        return task.call();
    }

    @Override
    public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        this.classFile = new ClassFile(className);
        return this.classFile;
    }

}