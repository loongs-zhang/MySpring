package com.dragon.springframework.aop.support;

import com.dragon.springframework.aop.Pointcut;

/**
 * 由使用String表达式的切入点实现的接口。
 *
 * @author SuccessZhang
 * @date 2020/07/04
 */
public interface ExpressionPointcut extends Pointcut {

    /**
     * 返回此切入点的String表达式。
     */
    String getExpression();

}
