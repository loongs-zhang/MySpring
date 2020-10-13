package com.example.design.strategy.after;

import com.example.design.strategy.before.ChargeStrategy;
import com.example.design.strategy.before.ChargeType;
import com.example.design.strategy.before.DiscountStrategy;
import com.example.design.strategy.before.NormalStrategy;
import com.example.design.strategy.before.RebateStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
public class ChargeContext {

    private static final Map<ChargeType, ChargeStrategy> STRATEGIES = new HashMap<>();

    private final ChargeStrategy strategy;

    static {
        STRATEGIES.put(ChargeType.NORMAL, NormalStrategy.INSTANCE);
        STRATEGIES.put(ChargeType.DISCOUNT, DiscountStrategy.INSTANCE);
        STRATEGIES.put(ChargeType.REBATE, RebateStrategy.INSTANCE);
        STRATEGIES.put(ChargeType.DEFAULT, NormalStrategy.INSTANCE);
    }

    public ChargeContext(ChargeType type) {
        this.strategy = STRATEGIES.get(type);
    }

    public double charge(double price) {
        return this.strategy.charge(price);
    }
}
