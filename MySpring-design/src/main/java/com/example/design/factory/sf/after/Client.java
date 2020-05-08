package com.example.design.factory.sf.after;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public class Client {
    public static void main(String[] args) {
        IPhone8 a = IPhoneFactory.create(IPhone8.class);
        IPhone9 b = IPhoneFactory.create(IPhone9.class);
        System.out.println(a);
        System.out.println(b);
    }
}
