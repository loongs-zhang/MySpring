package com.dragon.springframework.core.expression;

import java.lang.reflect.Method;

/**
 * 表达式解析的顶层接口。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public interface Parser {
    /**
     * 解析的最终结果要么匹配要么不匹配。
     *
     * @param target     匹配目标
     * @param expression 表达式
     * @return 解析的最终结果
     */
    boolean parse(Method target, String expression);
}
