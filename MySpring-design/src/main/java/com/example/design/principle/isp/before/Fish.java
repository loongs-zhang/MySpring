package com.example.design.principle.isp.before;

/**
 * @author SuccessZhang
 * @date 2020/05/05
 */
@SuppressWarnings("unused")
public class Fish implements Animal {
    @Override
    public void fly() {

    }

    @Override
    public void swim() {
        System.out.println("鱼会游泳");
    }
}
