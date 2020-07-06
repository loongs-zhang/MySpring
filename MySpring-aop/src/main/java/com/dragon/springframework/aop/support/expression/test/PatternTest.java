package com.dragon.springframework.aop.support.expression.test;

import java.util.regex.Pattern;

/**
 * @author SuccessZhang
 * @date 2020/07/05
 */
public class PatternTest {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("\\.\\.+");
        System.out.println(pattern.matcher("...").matches());
        System.out.println(pattern.matcher("..").matches());
    }
}
