package com.example.design.principle.carp.before;

/**
 * 开闭原则：对扩展开放，对修改关闭
 *
 * @author SuccessZhang
 * @date 2020/05/04
 */
@SuppressWarnings("unused")
public class DiscountCommodity extends Commodity {

    public DiscountCommodity(double price) {
        super(price);
    }

    @Override
    public double getOriginPrice() {
        return super.getOriginPrice();
    }

    @Override
    public double getSellPrice() {
        return super.getSellPrice() * 0.9;
    }
}
