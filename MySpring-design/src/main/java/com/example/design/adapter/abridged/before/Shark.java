package com.example.design.adapter.abridged.before;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
@SuppressWarnings("unused")
public class Shark implements Animal {

    @Override
    public void eat() {
        System.out.println("鲨鱼吃肉");
    }

    @Override
    public void fly() {

    }

    @Override
    public void swim() {
        System.out.println("鲨鱼会游泳");
    }

    @Override
    public void run() {

    }
}
