package com.example.design.observer.after;

import com.example.design.observer.before.State;

/**
 * 观察者
 *
 * @author SuccessZhang
 * @date 2020/06/01
 */
public interface Observer {

    /**
     * 响应老师状态的变化
     */
    void reply(State state);
}
