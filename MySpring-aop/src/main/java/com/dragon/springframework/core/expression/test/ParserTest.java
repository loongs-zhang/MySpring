package com.dragon.springframework.core.expression.test;

import com.dragon.springframework.core.expression.ParserContext;
import com.dragon.springframework.test.pojo.Type;
import com.dragon.springframework.test.service.TestService;

import java.lang.reflect.Method;

/**
 * @author SuccessZhang
 * @date 2020/07/01
 */
public class ParserTest {
    public static void main(String[] args) throws NoSuchMethodException {
        String expression = "* com..*Service.setType(java.lang.String,com.dragon.springframework.test.pojo.Type)";
        Method method = TestService.class.getDeclaredMethod("setType", String.class, Type.class);
        ParserContext context = new ParserContext();
        System.out.println(context.parse(method, expression));
    }
}
