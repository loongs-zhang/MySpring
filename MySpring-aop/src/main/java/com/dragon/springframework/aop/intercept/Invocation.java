package com.dragon.springframework.aop.intercept;

/**
 * 此接口表示程序中的调用，
 * 调用是连接点，可以被拦截器拦截。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public interface Invocation extends JoinPoint {

    /**
     * 获取参数作为数组对象，可以更改此
     * 数组中的元素值以更改参数。
     */
    Object[] getArguments();

}
