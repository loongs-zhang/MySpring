package com.dragon.springframework.aop.framework;

import com.dragon.springframework.aop.ProxyMethodInvocation;
import com.dragon.springframework.core.proxy.jdk.InvocationHandler;
import com.dragon.springframework.core.proxy.jdk.Proxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Spring AOP框架的基于JDK的{@link AopProxy}实现。
 * 原始对象应通过{@link AdvisedSupport}类配置的代理工厂获得。
 * 源码中通过DefaultAopProxyFactory自动创建基于CGLIB的代理，
 * 这里作者进行了简化，让{@link ProxyCreatorSupport}自动创建代理。
 *
 * @author SuccessZhang
 * @date 2020/07/03
 */
final class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;

    private final Class<?> targetClass;

    JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
        this.targetClass = advised.getTargetClass();
    }

    @Override
    public Object getProxy() {
        return this.getProxy(targetClass.getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader, targetClass.getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.targetClass);
        ProxyMethodInvocation methodInvocation = new ReflectiveMethodInvocation(proxy, this.advised.getTarget(), method, args, this.targetClass, chain);
        return methodInvocation.proceed();
    }
}
