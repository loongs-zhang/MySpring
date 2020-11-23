package com.example.design.observer.after;

import com.example.design.observer.before.State;

/**
 * 通知者
 *
 * @author SuccessZhang
 * @date 2020/06/01
 */
public interface Notifier {

    /**
     * 添加观察者
     */
    boolean addObserver(Observer observer);

    /**
     * 删除观察者
     */
    boolean removeObserver(Observer observer);

    /**
     * 通知其他小伙伴
     */
    void notify(State state);
}
