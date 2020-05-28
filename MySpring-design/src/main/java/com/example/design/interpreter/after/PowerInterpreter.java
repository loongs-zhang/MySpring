package com.example.design.interpreter.after;

/**
 * 我们扩展的求x的n次方解释器。
 *
 * @author SuccessZhang
 * @date 2020/05/28
 */
public class PowerInterpreter extends AbstractInterpreter {

    public PowerInterpreter(Interpreter left, Interpreter right) {
        super(left, right);
    }

    @Override
    public double operate(double left, double right) {
        return Math.pow(left, right);
    }

    @Override
    public int getPriority() {
        return 3;
    }
}
