package com.example.design.strategy.before;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
public enum NormalStrategy implements ChargeStrategy {

    /**
     * 枚举单例
     */
    INSTANCE;

    /**
     * 无折扣
     */
    @Override
    public double charge(double price) {
        return price;
    }
}
