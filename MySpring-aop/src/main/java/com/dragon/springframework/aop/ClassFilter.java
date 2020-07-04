package com.dragon.springframework.aop;

/**
 * 表达式解析的顶层接口。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public interface ClassFilter {

    /**
     * 解析的最终结果要么匹配要么不匹配。
     *
     * @param target     匹配目标
     * @param expression 表达式
     * @return 解析的最终结果
     */
    boolean matches(Class target, String expression);

    /**
     * 解析的最终结果要么匹配要么不匹配。
     *
     * @param target 匹配目标
     * @return 解析的最终结果
     */
    boolean matches(Class target);

}
