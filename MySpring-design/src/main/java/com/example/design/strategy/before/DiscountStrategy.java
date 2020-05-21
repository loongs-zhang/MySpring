package com.example.design.strategy.before;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
@SuppressWarnings("unused")
public enum DiscountStrategy implements ChargeStrategy {

    /**
     * 单例
     */
    INSTANCE(0.9);

    private final double discount;

    DiscountStrategy(double discount) {
        this.discount = discount;
    }

    @Override
    public double charge(double price) {
        return price * this.discount;
    }
}
