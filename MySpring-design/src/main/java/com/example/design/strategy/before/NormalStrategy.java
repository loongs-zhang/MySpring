package com.example.design.strategy.before;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
@SuppressWarnings("unused")
public enum NormalStrategy implements ChargeStrategy {

    /**
     * 单例
     */
    INSTANCE;

    @Override
    public double charge(double price) {
        return price;
    }
}
