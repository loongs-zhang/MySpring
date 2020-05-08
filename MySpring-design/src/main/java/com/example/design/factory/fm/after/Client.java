package com.example.design.factory.fm.after;

import com.example.design.factory.sf.after.IPhone8;
import com.example.design.factory.sf.after.IPhone9;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public class Client {
    public static void main(String[] args) {
        IPhone8Factory aFactory = new IPhone8Factory();
        IPhone9Factory bFactory = new IPhone9Factory();
        IPhone8 a = aFactory.create();
        IPhone9 b = bFactory.create();
        System.out.println(a);
        System.out.println(b);
    }
}
