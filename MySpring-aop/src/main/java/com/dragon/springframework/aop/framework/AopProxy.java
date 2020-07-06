package com.dragon.springframework.aop.framework;

/**
 * 用于配置的AOP代理的委托接口，
 * 允许创建实际的代理对象，
 * 可用于JDK动态代理和CGLIB代理。
 *
 * @author SuccessZhang
 * @date 2020/07/03
 */
public interface AopProxy {

    /**
     * 使用AopProxy的默认类加载器创建一个新的代理对象。
     */
    Object getProxy();

    /**
     * 使用给定的类加载器创建一个新的代理对象。
     */
    Object getProxy(ClassLoader classLoader);

}