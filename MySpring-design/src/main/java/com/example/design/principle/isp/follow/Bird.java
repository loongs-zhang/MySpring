package com.example.design.principle.isp.follow;

/**
 * @author SuccessZhang
 * @date 2020/05/05
 */
@SuppressWarnings("unused")
public class Bird implements FlyAnimal {
    @Override
    public void fly() {
        System.out.println("鸟会飞");
    }
}
