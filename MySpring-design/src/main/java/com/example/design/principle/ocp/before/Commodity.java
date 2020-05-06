package com.example.design.principle.ocp.before;

/**
 * 这是一个简单的商品类，需求如下：
 * 现在商场在搞活动，所有商品打九折。
 *
 * @author SuccessZhang
 * @date 2020/05/04
 * <p>
 * 546600665
 * 小米贷款
 */
@SuppressWarnings("unused")
public class Commodity implements Sell {

    /**
     * 商品原价
     */
    private double price;

    public Commodity(double price) {
        this.price = price;
    }

    /**
     * 我们约定此方法获取的是实际出售的价格
     */
    @Override
    public double getSellPrice() {
        // 最简单的方法当然是直接修改下面这行代码了
        return price;
    }

}
