package com.example.design.strategy.before;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
public class StrategyBeforeTest {
    public static void main(String[] args) {
        ChargeStrategy strategy1 = ChargeStrategyDelegator.choose(ChargeType.NORMAL);
        System.out.println(strategy1.charge(300));
        ChargeStrategy strategy2 = ChargeStrategyDelegator.choose(ChargeType.DISCOUNT);
        System.out.println(strategy2.charge(300));
        ChargeStrategy strategy3 = ChargeStrategyDelegator.choose(ChargeType.REBATE);
        System.out.println(strategy3.charge(300));
    }
}
