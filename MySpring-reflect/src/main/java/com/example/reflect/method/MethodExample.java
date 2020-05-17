package com.example.reflect.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author SuccessZhang
 * @date 2020/05/03
 */
public class MethodExample {
    public static void main(String[] args) {
        Class<?> userClass = com.example.reflect.User.class;
        try {
            Method method = userClass.getDeclaredMethod("hello");
            com.example.reflect.User user = new com.example.reflect.User();
            method.setAccessible(true);
            Object result = method.invoke(user);
            System.out.println(result);

            Method getHello = userClass.getDeclaredMethod("getHello");
            System.out.println(getHello.invoke(null));

            Method testArgs = userClass.getDeclaredMethod("testArgs", String[].class);
            try {
                testArgs.invoke(null, new String[]{"1", "2"});
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("invoke failed");
            }
            testArgs.invoke(null, (Object) new String[]{"1", "2"});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
