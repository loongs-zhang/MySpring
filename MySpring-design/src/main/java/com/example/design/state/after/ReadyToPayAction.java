package com.example.design.state.after;

import com.example.design.state.before.State;

/**
 * 准备付款动作，比如唤起支付宝，但还未支付。
 *
 * @author SuccessZhang
 * @date 2020/06/02
 */
public class ReadyToPayAction implements StateAction {
    @Override
    public void act(Order2 order) {
        System.out.println(order.getCurrentState());
        order.setStateAction(new PayAction());
    }

    @Override
    public State getCurrentState() {
        return State.UNPAID;
    }
}
