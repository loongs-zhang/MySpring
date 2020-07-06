package com.dragon.springframework.aop.framework;

/**
 * 规范了基于{@link AdvisedSupport}配置对象创建AOP代理的工厂。
 *
 * @author SuccessZhang
 * @date 2020/07/05
 */
public interface AopProxyFactory {

    /**
     * 为给定的AOP配置创建一个{@link AopProxy}。
     */
    AopProxy createAopProxy(AdvisedSupport config) throws RuntimeException;

}
