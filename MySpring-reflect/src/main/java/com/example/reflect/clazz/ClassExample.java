package com.example.reflect.clazz;

/**
 * @author SuccessZhang
 * @date 2020/04/29
 */
public class ClassExample {
    public static void main(String[] args) {
        // 任何类都有一个隐含的静态成员变量class
        Class<?> userClass = com.example.reflect.User.class;
        System.out.println(userClass);
        // 已知该类的对象，可以通过对象的getClass()方法获取
        com.example.reflect.User user = new com.example.reflect.User(123456);
        userClass = user.getClass();
        System.out.println(userClass);
        // 直接通过Class.forName(String className)来加载类
        // 这个在我们使用jdbc原生api时一定会用到
        try {
            userClass = Class.forName("com.example.reflect.User");
            System.out.println(userClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
