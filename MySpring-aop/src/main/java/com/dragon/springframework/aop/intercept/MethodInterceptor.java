package com.dragon.springframework.aop.intercept;

/**
 * 拦截在到达目标的接口上的调用，这些嵌套在目标的“顶部”。
 * 用户应实现{@link #invoke(MethodInvocation)}方法来修改原始行为。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
@FunctionalInterface
public interface MethodInterceptor extends Interceptor {

    /**
     * 实现此方法以在调用之前和之后执行额外的处理，
     * 符合规范的实现肯定会调用{@link JoinPoint＃proceed}。
     */
    Object invoke(MethodInvocation invocation) throws Throwable;

}
