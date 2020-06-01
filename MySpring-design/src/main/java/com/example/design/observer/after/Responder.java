package com.example.design.observer.after;

import com.example.design.observer.before.State;
import lombok.Data;

/**
 * @author SuccessZhang
 * @date 2020/06/01
 */
@Data
public class Responder implements Observer {

    protected State state;

    protected final Teacher2 teacher;

    @Override
    public void reply(State state) {
        this.state = state;
    }
}
