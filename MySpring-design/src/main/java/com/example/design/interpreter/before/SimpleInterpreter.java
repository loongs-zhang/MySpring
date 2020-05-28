package com.example.design.interpreter.before;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author SuccessZhang
 * @date 2020/05/28
 */
public class SimpleInterpreter {

    /**
     * 计算简单表达式
     *
     * @param expression 表达式，比如：7+3*4*5+2+4-3-1
     */
    public double interpret(String expression) {
        List<String> symbols = Arrays.asList("+", "-", "*", "/");
        Stack<Double> ns = new Stack<>();
        Stack<String> ss = new Stack<>();
        double number = 0;
        boolean isNumber = false;
        for (String symbol : expression.split(" ")) {
            if (symbols.contains(symbol)) {
                if (isNumber) {
                    ns.push(number);
                    number = 0;
                    isNumber = false;
                }
                if (getPriority(symbol) > 0) {
                    if (ss.isEmpty()) {
                        ss.push(symbol);
                        continue;
                    }
                    if (getPriority(ss.peek()) >= getPriority(symbol)) {
                        ns.push(calculate(ns.pop(), ns.pop(), ss.pop()));
                    }
                    ss.push(symbol);
                }
                continue;
            }
            number = Integer.parseInt(symbol);
            isNumber = true;
        }
        if (isNumber) {
            ns.push(number);
        }
        //符号栈不为空，再次计算
        while (!ss.isEmpty()) {
            ns.push(calculate(ns.pop(), ns.pop(), ss.pop()));
        }
        return ns.pop();
    }

    /**
     * 返回运算符的优先级
     */
    private int getPriority(String symbol) {
        if ("+".equals(symbol) || "-".equals(symbol)) {
            return 1;
        } else if ("*".equals(symbol) || "/".equals(symbol)) {
            return 2;
        }
        return 0;
    }

    /**
     * 入栈再出栈，左右就反过来了
     */
    public double calculate(double right, double left, String symbol) {
        if ("+".equals(symbol)) {
            return left + right;
        } else if ("-".equals(symbol)) {
            return left - right;
        } else if ("*".equals(symbol)) {
            return left * right;
        } else if ("/".equals(symbol)) {
            return left / right;
        }
        throw new UnsupportedOperationException("不支持的运算符");
    }

    public static void main(String[] args) {
        SimpleInterpreter interpreter = new SimpleInterpreter();
        System.out.println(interpreter.interpret("1 + 2 * 3"));
        System.out.println(interpreter.interpret("1 * 2 + 3 * 4"));
    }
}
