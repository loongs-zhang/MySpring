package com.example.design.observer.after;

import com.example.design.observer.before.State;

/**
 * @author SuccessZhang
 * @date 2020/06/01
 */
public interface Notifier {
    boolean addObserver(Observer observer);

    boolean removeObserver(Observer observer);

    void notify(State state);
}
