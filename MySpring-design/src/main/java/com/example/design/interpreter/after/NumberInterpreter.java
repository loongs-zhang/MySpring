package com.example.design.interpreter.after;

/**
 * @author SuccessZhang
 * @date 2020/05/28
 */
public class NumberInterpreter implements Interpreter {

    private final double value;

    public NumberInterpreter(String value) {
        this.value = Double.valueOf(value);
    }

    @Override
    public double interpret() {
        return this.value;
    }

    @Override
    public int getPriority() {
        return 0;
    }

}
