package com.example.reflect;

import lombok.Data;

/**
 * @author SuccessZhang
 * @date 2020/04/29
 */
@Data
public class User {

    public String name;

    private Integer age;

    public final long account;

    public User() {
        this.account = -1;
    }

    public User(long account) {
        this.account = account;
    }

    private void hello() {
        System.out.println("hello");
    }

    public void hello(String target) {
        System.out.println(target);
    }

    public static String getHello() {
        return "hello";
    }
}