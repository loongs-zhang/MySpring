package com.example.design.adapter.before;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
public class AdapterBeforeTest {
    public static void main(String[] args) {
        AlternatingCurrent220V ac220V = new AlternatingCurrent220V();
        System.out.println(ac220V.changeToDC20V().getVoltage());
        System.out.println(ac220V.changeToDC110V().getVoltage());
    }
}
