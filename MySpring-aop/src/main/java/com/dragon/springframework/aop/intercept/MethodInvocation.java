package com.dragon.springframework.aop.intercept;

import java.lang.reflect.Method;

/**
 * 方法调用的描述，在方法调用时提供给拦截器。
 * 方法调用是一个连接点，并且可以被方法拦截器拦截。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public interface MethodInvocation extends Invocation {

    /**
     * 获取被调用的方法。
     */
    Method getMethod();

}
