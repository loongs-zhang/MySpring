package com.example.design.principle.ocp.before;

/**
 * 这是一个简单的商品类，需求如下：
 * 现在商场在搞活动，所有商品打九折。
 *
 * @author SuccessZhang
 * @date 2020/05/04
 */
@SuppressWarnings("unused")
public class Commodity2 implements Sell {

    /**
     * 商品原价
     */
    private double price;

    public Commodity2(double price) {
        this.price = price;
    }

    public double getOriginPrice() {
        return price;
    }

    /**
     * 我们约定此方法获取的是实际出售的价格
     */
    @Override
    public double getSellPrice() {
        // 但是这样可能导致其他地方调用结果错误
        return price * 0.9;
    }

}
