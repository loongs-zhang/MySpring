package com.example.design.state.before;

/**
 * @author SuccessZhang
 * @date 2020/06/02
 */
public enum State {
    //提交订单
    SUBMITTED,
    //未付款
    UNPAID,
    //已付款
    PAID,
    //未发货
    NOT_SHIPPED,
    //已发货
    SHIPPED,
    //运输途中
    DELIVERING,
    //已确认收货
    CONFIRMED,
    //关闭
    CLOSED
}
