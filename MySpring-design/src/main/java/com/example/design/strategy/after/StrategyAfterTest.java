package com.example.design.strategy.after;

import com.example.design.strategy.before.ChargeType;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
public class StrategyAfterTest {
    public static void main(String[] args) {
        ChargeContext context1 = new ChargeContext(ChargeType.NORMAL);
        System.out.println(context1.charge(300));
        ChargeContext context2 = new ChargeContext(ChargeType.DISCOUNT);
        System.out.println(context2.charge(300));
        ChargeContext context3 = new ChargeContext(ChargeType.REBATE);
        System.out.println(context3.charge(300));
    }
}
