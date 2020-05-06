package com.example.design.principle.dip.follow;

/**
 * @author SuccessZhang
 * @date 2020/05/05
 */
@SuppressWarnings("unused")
public class Chinese implements Language {
    @Override
    public void hello() {
        System.out.println("你好");
    }
}
