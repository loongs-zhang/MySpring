package com.dragon.springframework.aop;

/**
 * 限制切入点与一组给定目标类的匹配的过滤器。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public interface ClassFilter {

    /**
     * 判断切入点是否应该应用于给定的接口或目标类。
     *
     * @param target     匹配目标
     * @param expression 表达式
     * @return 解析的最终结果
     */
    boolean matches(Class target, String expression);

    /**
     * 判断切入点是否应该应用于给定的接口或目标类。
     *
     * @param target 匹配目标
     * @return 解析的最终结果
     */
    boolean matches(Class target);

}
