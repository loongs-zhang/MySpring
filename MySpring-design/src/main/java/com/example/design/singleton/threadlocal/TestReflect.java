package com.example.design.singleton.threadlocal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
public class TestReflect {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<?>[] constructors = ThreadLocalSingleton.class.getDeclaredConstructors();
        Constructor<?> constructor = constructors[0];
        constructor.setAccessible(true);
        //System.out.println(constructor.newInstance());
        System.out.println(ThreadLocalSingleton.getInstance());
    }
}
