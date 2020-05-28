package com.example.design.interpreter.after;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author SuccessZhang
 * @date 2020/05/28
 */
public class ExpressionInterpreter implements Interpreter {

    private final List<String> operators = Arrays.asList("+", "-", "*", "/", "^");

    private final Stack<Interpreter> stack = new Stack<>();

    public double parse(String expression) {
        Interpreter left, right;
        String[] symbols = expression.split(" ");
        for (int i = 0; i < symbols.length; i++) {
            String symbol = symbols[i];
            if (isOperator(symbol)) {
                left = stack.pop();
                right = new NumberInterpreter(symbols[++i]);
                stack.push(createInterpreter(left, right, symbol));
                continue;
            }
            stack.push(new NumberInterpreter(symbols[i]));
        }
        return this.interpret();
    }

    private AbstractInterpreter createInterpreter(Interpreter left, Interpreter right, String symbol) {
        switch (symbol) {
            case "+":
                return new AdditionInterpreter(left, right);
            case "-":
                return new SubtractionInterpreter(left, right);
            case "*":
                return new MultiplicationInterpreter(left, right);
            case "/":
                return new DivisionInterpreter(left, right);
            case "^":
                return new PowerInterpreter(left, right);
            default:
                throw new UnsupportedOperationException("不支持的运算符");
        }
    }

    private boolean isOperator(String symbol) {
        return operators.contains(symbol);
    }

    @Override
    public double interpret() {
        return stack.pop().interpret();
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

}
