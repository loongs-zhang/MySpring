package com.dragon.springframework.aop;

import java.lang.reflect.Method;

/**
 * {@link Pointcut}的一部分，检查目标方法是否符合通知。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public interface MethodMatcher {

    /**
     * 检查给定的方法是否匹配。
     *
     * @param target     匹配目标
     * @param expression 表达式
     * @return 解析的最终结果
     */
    boolean matches(Method target, String expression);

    /**
     * 检查给定的方法是否匹配。
     *
     * @param target 匹配目标
     * @return 解析的最终结果
     */
    boolean matches(Method target);

}
