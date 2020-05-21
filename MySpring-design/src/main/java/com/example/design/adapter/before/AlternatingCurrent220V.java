package com.example.design.adapter.before;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
@SuppressWarnings("unused")
public class AlternatingCurrent220V {

    private final int voltage = 220;

    public DirectCurrent20V changeToDC20V() {
        return new DirectCurrent20V(voltage / 11);
    }

    public DirectCurrent110V changeToDC110V() {
        return new DirectCurrent110V(voltage / 2);
    }
}
