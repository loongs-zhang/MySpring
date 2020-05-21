package com.example.design.adapter.after;

/**
 * @author SuccessZhang
 * @date 2020/05/21
 */
public class AdapterAfterTest {
    public static void main(String[] args) {
        AlternatingCurrent220V ac220V = new AlternatingCurrent220V();
        PowerAdapter adapter = new PowerAdapter(ac220V);
        System.out.println(adapter.changeToDC20V().getVoltage());
        System.out.println(adapter.changeToDC110V().getVoltage());
    }
}
