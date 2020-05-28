package com.example.design.interpreter.after;

/**
 * @author SuccessZhang
 * @date 2020/05/28
 */
public class InterpreterTest {
    public static void main(String[] args) {
        ExpressionInterpreter interpreter = new ExpressionInterpreter();
        System.out.println(interpreter.parse("1 + 2 * 3"));
        System.out.println(interpreter.parse("1 / 2 + 3 ^ 4"));
    }
}
