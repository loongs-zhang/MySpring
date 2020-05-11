package com.example.design.singleton.innerstatic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
public class TestReflect {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<InnerStaticLazySingleton> constructor = InnerStaticLazySingleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        System.out.println(constructor.newInstance());
    }
}
