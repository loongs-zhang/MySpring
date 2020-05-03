package com.example.reflect.field;

import java.lang.reflect.Field;

/**
 * @author SuccessZhang
 * @date 2020/05/02
 */
public class FieldExample {
    public static void main(String[] args) {
        Class<?> userClass = com.example.reflect.User.class;
        try {
            Field field = userClass.getDeclaredField("account");
            com.example.reflect.User user = new com.example.reflect.User();
            // 设置绕过访问检查
            field.setAccessible(true);
            System.out.println(user);
            // 可以通过field.get来获取指定对象的指定字段的值
            System.out.println(field.get(user));
            // 可以通过field.set来设置指定对象的指定字段的值
            field.setLong(user, 0L);
            System.out.println(user);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
