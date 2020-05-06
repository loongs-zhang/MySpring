package com.example.design.principle.isp.follow;

/**
 * @author SuccessZhang
 * @date 2020/05/05
 */
@SuppressWarnings("unused")
public class Fish implements SwimAnimal {
    @Override
    public void swim() {
        System.out.println("鱼会游泳");
    }
}
