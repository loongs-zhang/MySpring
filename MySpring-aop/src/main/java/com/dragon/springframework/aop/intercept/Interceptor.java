package com.dragon.springframework.aop.intercept;

import com.dragon.springframework.aop.Advice;

/**
 * 此接口表示通用拦截器，通用拦截器可以拦截
 * 基本程序中发生的运行时事件，这些事件通过连接点实现。
 * 运行时连接点可以是调用，字段访问，异常等等。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public interface Interceptor extends Advice {

}
