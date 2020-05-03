package com.example.reflect.constructor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author SuccessZhang
 * @date 2020/05/02
 */
public class ConstructorExample {
    public static void main(String[] args) {
        Class<?> userClass = com.example.reflect.User.class;
        try {
            Constructor<?> constructor = userClass.getConstructor();
            Constructor<?>[] constructors = userClass.getConstructors();
            constructor = userClass.getDeclaredConstructor();
            constructors = userClass.getDeclaredConstructors();
            System.out.println(constructor.newInstance());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
