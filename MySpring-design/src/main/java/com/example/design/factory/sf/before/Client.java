package com.example.design.factory.sf.before;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public class Client {
    public static void main(String[] args) {
        IPhone8 a = new IPhone8();
        IPhone9 b = new IPhone9();
        System.out.println(a);
        System.out.println(b);
    }
}
