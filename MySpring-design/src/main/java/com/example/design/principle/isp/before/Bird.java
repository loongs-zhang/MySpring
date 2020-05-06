package com.example.design.principle.isp.before;

/**
 * @author SuccessZhang
 * @date 2020/05/05
 */
@SuppressWarnings("unused")
public class Bird implements Animal {
    @Override
    public void fly() {
        System.out.println("鸟会飞");
    }

    @Override
    public void swim() {

    }
}
