package com.example.design.principle.srp.follow;

/**
 * @author SuccessZhang
 * @date 2020/05/05
 */
@SuppressWarnings("unused")
public class ReduceCalculator implements Calculator {
    @Override
    public double calculate(double arg1, double arg2) {
        return Math.abs(arg1 - arg2);
    }
}
