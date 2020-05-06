package com.example.design.principle.ocp.before;

/**
 * @author SuccessZhang
 * @date 2020/05/04
 */
public interface Sell {

    /**
     * 我们约定此方法获取的是商品实际出售的价格
     *
     * @return 商品实际出售的价格
     */
    double getSellPrice();
}
