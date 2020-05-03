package com.example.reflect.parameter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author SuccessZhang
 * @date 2020/05/03
 */
public class ParameterExample {
    public static void main(String[] args) {
        Class<?> userClass = com.example.reflect.User.class;
        try {
            Method method = userClass.getDeclaredMethod("hello", String.class);
            Parameter[] parameters = method.getParameters();
            Parameter parameter = parameters[0];
            System.out.println(parameter.getType().getName() + " " + parameter.getName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
