package com.example.design.adapter.after;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
public class DirectCurrent110V extends AbstractDirectCurrent {

    public DirectCurrent110V(int voltage) {
        super(voltage);
    }

    @Override
    protected boolean judge(int voltage) {
        return voltage == 110;
    }
}
