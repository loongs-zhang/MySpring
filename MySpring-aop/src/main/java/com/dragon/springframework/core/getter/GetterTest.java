package com.dragon.springframework.core.getter;

import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author SuccessZhang
 * @date 2020/07/18
 */
@Getter
public class GetterTest {

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        Getter getter = GetterTest.class.getDeclaredAnnotation(Getter.class);
        System.out.println(getter);
        InvocationHandler invocationHandler = Proxy.getInvocationHandler(getter);
        Field field = invocationHandler.getClass().getDeclaredField("memberValues");
        field.setAccessible(true);
        Map<String, Object> memberValues = (Map<String, Object>) field.get(getter);
        System.out.println(memberValues);
    }
}
