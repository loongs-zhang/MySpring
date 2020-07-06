package com.dragon.springframework.aop.intercept;

/**
 * 公开{@link #proceed(Object[])}
 * 方法以支持环绕通知。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public interface ProceedingJoinPoint extends JoinPoint {

    /**
     * 继续执行链中的下一个拦截器，
     * 与{@link #proceed()}不同的是，
     * 此方法可以使用指定参数执行。
     */
    Object proceed(Object[] args) throws Throwable;
}
