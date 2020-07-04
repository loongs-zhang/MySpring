package com.dragon.springframework.aop.support.expression.test;

import com.dragon.springframework.aop.support.expression.ExpressionMatcher;
import com.dragon.springframework.test.pojo.Type;
import com.dragon.springframework.test.service.TestService;

import java.lang.reflect.Method;

/**
 * @author SuccessZhang
 * @date 2020/07/01
 */
public class MatcherTest {
    public static void main(String[] args) throws NoSuchMethodException {
        String expression = "* com..*Service.setType(java.lang.String,com.dragon.springframework.test.pojo.Type)";
        Method method = TestService.class.getDeclaredMethod("setType", String.class, Type.class);
        ExpressionMatcher context = new ExpressionMatcher();
        System.out.println(context.matches(method, expression));
    }
}
