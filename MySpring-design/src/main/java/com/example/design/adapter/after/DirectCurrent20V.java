package com.example.design.adapter.after;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
public class DirectCurrent20V extends AbstractDirectCurrent {

    public DirectCurrent20V(int voltage) {
        super(voltage);
    }

    @Override
    protected boolean judge(int voltage) {
        return voltage == 20;
    }
}
