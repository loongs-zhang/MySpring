package com.example.design.state.after;

import com.example.design.state.before.State;

/**
 * 运输动作，比如快递小哥上门取件，并送往下个接收点。
 *
 * @author SuccessZhang
 * @date 2020/06/02
 */
public class DeliverAction implements StateAction {
    @Override
    public void act(Order2 order) {
        System.out.println(order.getCurrentState());
        order.setStateAction(new ConfirmAction());
    }

    @Override
    public State getCurrentState() {
        return State.DELIVERING;
    }
}
