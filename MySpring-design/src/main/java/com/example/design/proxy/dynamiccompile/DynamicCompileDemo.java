package com.example.design.proxy.dynamiccompile;

import com.example.design.proxy.dynamiccompile.compile.ClassFileManager;
import com.example.design.proxy.dynamiccompile.compile.JavaFile;
import com.example.design.proxy.dynamiccompile.load.MyClassLoader;

import java.lang.reflect.Method;

/**
 * @author SuccessZhang
 * @date 2020/05/17
 */
public class DynamicCompileDemo {
    public static void main(String[] args) throws Exception {
        String source = "package " + DynamicCompileDemo.class.getPackage().getName() + ";\n" +
                "import java.util.Arrays;\n" +
                "public class HelloWorld{\n" +
                "public static void main(String[] args) {\n" +
                "System.out.println(Arrays.toString(args) +\" Hello World\");\n" +
                "}\n" +
                "}";
        ClassFileManager manager = new ClassFileManager();
        JavaFile javaFile = new JavaFile("HelloWorld", source);
        if (!manager.compile(javaFile)) {
            System.out.println("编译失败");
            return;
        }
        System.out.println("编译成功");
        //加载Class
        MyClassLoader loader = new MyClassLoader(manager.getClassFile());
        Class<?> helloWorld = loader.loadClass("HelloWorld");
        Method main = helloWorld.getDeclaredMethod("main", String[].class);
        main.invoke(null, (Object) new String[]{"1", "2"});
    }
}
