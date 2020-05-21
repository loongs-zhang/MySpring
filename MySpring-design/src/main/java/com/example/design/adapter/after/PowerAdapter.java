package com.example.design.adapter.after;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
public class PowerAdapter {

    private final AlternatingCurrent220V ac220V;

    public PowerAdapter(AlternatingCurrent220V ac220V) {
        this.ac220V = ac220V;
    }

    public DirectCurrent20V changeToDC20V() {
        return new DirectCurrent20V(ac220V.getVoltage() / 11);
    }

    public DirectCurrent110V changeToDC110V() {
        return new DirectCurrent110V(ac220V.getVoltage() / 2);
    }
}
