package com.example.design.state.after;

import com.example.design.state.before.State;

/**
 * (买家)支付动作，比如唤起支付宝，指纹识别后付款。
 *
 * @author SuccessZhang
 * @date 2020/06/02
 */
public class PayAction implements StateAction {
    @Override
    public void act(Order2 order) {
        System.out.println(order.getCurrentState());
        order.setStateAction(new ReadyToShipAction());
    }

    @Override
    public State getCurrentState() {
        return State.PAID;
    }
}
