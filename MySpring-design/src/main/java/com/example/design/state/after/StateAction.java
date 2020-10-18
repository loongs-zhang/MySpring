package com.example.design.state.after;

import com.example.design.state.before.State;

/**
 * @author SuccessZhang
 * @date 2020/06/02
 */
public interface StateAction {

    /**
     * 订单状态流转，继续往下走。
     */
    void act(Order2 order);

    /**
     * 获取当前订单状态。
     */
    State getCurrentState();
}
