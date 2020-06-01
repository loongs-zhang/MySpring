package com.example.design.observer.after;

import com.example.design.observer.before.State;

/**
 * @author SuccessZhang
 * @date 2020/06/01
 */
public interface Observer {
    void reply(State state);
}
