package com.example.design.strategy.before;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
public enum RebateStrategy implements ChargeStrategy {

    /**
     * 枚举单例
     */
    INSTANCE(300, 100);

    private final double condition;

    private final double rebate;

    RebateStrategy(double condition, double rebate) {
        this.condition = condition;
        this.rebate = rebate;
    }

    /**
     * 满减价
     */
    @Override
    public double charge(double price) {
        double result = price;
        if (result >= this.condition) {
            result = result - Math.floor(result / condition) * rebate;
        }
        return result;
    }
}
