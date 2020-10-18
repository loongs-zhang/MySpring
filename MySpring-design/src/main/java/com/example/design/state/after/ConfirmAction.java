package com.example.design.state.after;

import com.example.design.state.before.State;

/**
 * (买家)确认收货动作。
 *
 * @author SuccessZhang
 * @date 2020/06/02
 */
public class ConfirmAction implements StateAction {
    @Override
    public void act(Order2 order) {
        System.out.println(order.getCurrentState());
        order.setStateAction(new CloseAction());
    }

    @Override
    public State getCurrentState() {
        return State.CONFIRMED;
    }
}
