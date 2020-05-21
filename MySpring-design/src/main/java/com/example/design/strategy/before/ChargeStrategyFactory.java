package com.example.design.strategy.before;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
@SuppressWarnings("unused")
public class ChargeStrategyFactory {

    public static ChargeStrategy create(ChargeType type) {
        ChargeStrategy charge;
        switch (type) {
            case NORMAL:
            default:
                charge = NormalStrategy.INSTANCE;
                break;
            case DISCOUNT:
                charge = DiscountStrategy.INSTANCE;
                break;
            case REBATE:
                charge = RebateStrategy.INSTANCE;
                break;
        }
        return charge;
    }

}
