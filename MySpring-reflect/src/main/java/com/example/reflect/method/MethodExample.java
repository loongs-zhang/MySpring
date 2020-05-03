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
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
