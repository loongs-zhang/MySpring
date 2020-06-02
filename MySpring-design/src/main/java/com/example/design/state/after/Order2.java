package com.example.design.state.after;

import com.example.design.state.before.State;
import lombok.Setter;

/**
 * @author SuccessZhang
 * @date 2020/06/02
 */
public class Order2 {

    @Setter
    private StateAction stateAction;

    public Order2() {
        this.stateAction = new SubmitAction();
    }

    public void act() {
        if (this.stateAction == null) {
            return;
        }
        this.stateAction.act(this);
    }

    public State getCurrentState() {
        return this.stateAction.getCurrentState();
    }
}
