package com.dragon.springframework.aop;

/**
 * 核心Spring切入点抽象，切入点由{@link ClassFilter}和
 * {@link MethodMatcher}组成，这些基本术语和Pointcut本身
 * 都可以组合起来。
 *
 * @author SuccessZhang
 * @date 2020/07/02
 */
public interface Pointcut {

    /**
     * 返回此切入点的ClassMatcher。
     */
    ClassFilter getClassFilter();

    /**
     * 返回此切入点的MethodMatcher。
     */
    MethodMatcher getMethodMatcher();
}
