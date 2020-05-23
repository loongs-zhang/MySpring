package com.example.design.template.after;

/**
 * @author SuccessZhang
 * @date 2020/05/23
 */
@SuppressWarnings("unused")
public class SteakChef2 extends AbstractChef {

    @Override
    protected void buyFood() {
        System.out.println("买牛肉");
    }

    @Override
    protected void washFood() {
        System.out.println("反复清洗牛肉");
    }

    @Override
    protected void cutUpFood() {
        System.out.println("切成牛排");
    }

    @Override
    protected void stirFry() {
        System.out.println("二号大厨的牛排做法");
    }

    @Override
    protected void putOnPlate() {
        System.out.println("二号大厨的摆盘法");
    }
}
