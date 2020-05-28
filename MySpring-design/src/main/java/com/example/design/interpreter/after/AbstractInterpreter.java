package com.example.design.interpreter.after;

/**
 * @author SuccessZhang
 * @date 2020/05/28
 */
public abstract class AbstractInterpreter implements Interpreter {

    private final Interpreter left;

    private final Interpreter right;

    public AbstractInterpreter(Interpreter left, Interpreter right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public final double interpret() {
        int leftPriority = left.getPriority();
        int rightPriority = right.getPriority();
        if (leftPriority < rightPriority) {
            AbstractInterpreter interpreter = ((AbstractInterpreter) right);
            return interpreter.operate(
                    this.operate(left.interpret(), interpreter.left.interpret()),
                    interpreter.right.interpret());
        } else if (leftPriority > rightPriority) {
            AbstractInterpreter interpreter = ((AbstractInterpreter) left);
            return interpreter.operate(interpreter.left.interpret(),
                    this.operate(interpreter.right.interpret(), right.interpret()));
        }
        return this.operate(left.interpret(), right.interpret());
    }

    public abstract double operate(double left, double right);
}
