package com.example.design.adapter.after;

import lombok.Getter;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
public abstract class AbstractDirectCurrent {

    @Getter
    protected Integer voltage;

    public AbstractDirectCurrent(int voltage) {
        if (judge(voltage)) {
            this.voltage = voltage;
        }
    }

    protected abstract boolean judge(int voltage);
}
