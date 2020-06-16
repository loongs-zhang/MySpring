package com.example.demo.annotation.principle;

import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author SuccessZhang
 * @date 2020/06/16
 */
@Component
public class AnnotationPrinciple2 {
    public static void main(String[] args) {
        Component proxy = AnnotationPrinciple2.class
                .getAnnotation(Component.class);
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(proxy);
        System.out.println(invocationHandler.getClass());
    }
}
