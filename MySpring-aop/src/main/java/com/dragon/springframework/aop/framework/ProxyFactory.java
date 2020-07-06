package com.dragon.springframework.aop.framework;

import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * 用于AOP代理的工厂，提供简单的方式来获取AOP代理实例。
 *
 * @author SuccessZhang
 * @date 2020/07/03
 */
@NoArgsConstructor
public class ProxyFactory extends ProxyCreatorSupport {

    public ProxyFactory(Object target) {
        setTarget(target);
        Class<?> targetClass = target.getClass();
        setTargetClass(targetClass);
        setInterfaces(Arrays.asList(targetClass.getInterfaces()));
    }

    /**
     * 使用默认的类加载器创建一个新的代理对象。
     */
    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    /**
     * 使用给定的类加载器创建一个新的代理对象。
     */
    public Object getProxy(ClassLoader classLoader) {
        return createAopProxy().getProxy(classLoader);
    }
}
