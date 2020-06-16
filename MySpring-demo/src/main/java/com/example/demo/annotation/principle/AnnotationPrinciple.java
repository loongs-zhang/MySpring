package com.example.demo.annotation.principle;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author SuccessZhang
 * @date 2020/06/16
 */
@Component
public class AnnotationPrinciple {
    public static void main(String[] args) {
        Component annotation = AnnotationPrinciple.class
                .getAnnotation(Component.class);
        System.out.println(annotation.getClass());

        Object proxy = Proxy.newProxyInstance(
                Component.class.getClassLoader(),
                new Class<?>[]{Component.class},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return null;
                    }
                });
        System.out.println(proxy.getClass());
        System.out.println(proxy.getClass().equals(annotation.getClass()));
    }
}
