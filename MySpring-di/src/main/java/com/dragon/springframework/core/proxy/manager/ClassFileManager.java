package com.dragon.springframework.core.proxy.manager;

import com.dragon.springframework.core.proxy.source.ClassFile;
import com.dragon.springframework.core.proxy.source.CodeFile;
import lombok.Getter;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.nio.charset.Charset;
import java.util.Collections;

/**
 * 管理class文件
 *
 * @author SuccessZhang
 * @date 2020/05/13
 */
public class ClassFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    /**
     * 1.获取编译器
     */
    private static final JavaCompiler COMPILER = ToolProvider.getSystemJavaCompiler();

    @Getter
    private ClassFile classFile;

    public ClassFileManager() {
        /*2.获取标准文件管理器*/
        super(COMPILER.getStandardFileManager(null, null, Charset.forName("UTF-8")));
    }

    public boolean compile(CodeFile codeSource) {
        JavaCompiler.CompilationTask task = COMPILER.getTask(null, this, null, null, null,
                Collections.singletonList(codeSource));
        return task.call();
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
        this.classFile = new ClassFile(className, kind);
        return this.classFile;
    }

}