package com.example.design.strategy.before;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
public enum DiscountStrategy implements ChargeStrategy {

    /**
     * 枚举单例
     */
    INSTANCE(0.9);

    private final double discount;

    DiscountStrategy(double discount) {
        this.discount = discount;
    }

    /**
     * 九折折扣价
     */
    @Override
    public double charge(double price) {
        return price * this.discount;
    }
}
