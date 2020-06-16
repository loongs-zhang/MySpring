package com.example.demo.annotation.principle;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author SuccessZhang
 * @date 2020/06/16
 * @see sun.reflect.annotation.AnnotationInvocationHandler#memberValues
 */
@Component(value = "张三")
public class AnnotationPrinciple3 {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        Component proxy = AnnotationPrinciple3.class
                .getAnnotation(Component.class);
        InvocationHandler handler = Proxy.getInvocationHandler(proxy);
        Field field = handler.getClass().getDeclaredField("memberValues");
        field.setAccessible(true);
        Map<String, Object> memberValues = (Map<String, Object>) field.get(handler);
        System.out.println("memberValues->" + memberValues);
    }
}
