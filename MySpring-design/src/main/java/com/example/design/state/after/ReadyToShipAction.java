package com.example.design.state.after;

import com.example.design.state.before.State;

/**
 * @author SuccessZhang
 * @date 2020/06/02
 */
public class ReadyToShipAction implements StateAction {
    @Override
    public void act(Order2 order) {
        System.out.println(order.getCurrentState());
        order.setStateAction(new ShipAction());
    }

    @Override
    public State getCurrentState() {
        return State.NOT_SHIPPED;
    }
}
