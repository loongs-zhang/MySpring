package com.example.design.strategy.before;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
public class ChargeStrategyDelegator {

    public static ChargeStrategy choose(ChargeType type) {
        switch (type) {
            case NORMAL:
            default:
                return NormalStrategy.INSTANCE;
            case DISCOUNT:
                return DiscountStrategy.INSTANCE;
            case REBATE:
                return RebateStrategy.INSTANCE;
        }
    }

}
