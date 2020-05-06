package com.example.design.principle.srp.before;

/**
 * @author SuccessZhang
 * @date 2020/05/05
 */
@SuppressWarnings("unused")
public class Calculator {
    public double calculate(String model, double arg1, double arg2) {
        if ("+".equals(model)) {
            return arg1 + arg2;
        } else if ("-".equals(model)) {
            return arg1 - arg2;
        }
        throw new RuntimeException("wrong model");
    }
}
