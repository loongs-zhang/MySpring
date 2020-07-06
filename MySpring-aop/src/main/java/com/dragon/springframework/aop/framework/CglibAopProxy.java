package com.dragon.springframework.aop.framework;

import com.dragon.springframework.aop.ProxyMethodInvocation;
import com.dragon.springframework.core.proxy.cglib.Enhancer;
import com.dragon.springframework.core.proxy.cglib.MethodInterceptor;
import com.dragon.springframework.core.proxy.cglib.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Spring AOP框架的基于CGLIB的{@link AopProxy}实现。
 * 原始对象应通过{@link AdvisedSupport}对象配置的代理工厂获得。
 * 源码中通过DefaultAopProxyFactory自动创建基于CGLIB的代理，
 * 这里作者进行了简化，让{@link ProxyCreatorSupport}自动创建代理。
 *
 * @author SuccessZhang
 * @date 2020/07/03
 */
final class CglibAopProxy implements AopProxy, MethodInterceptor {

    private final AdvisedSupport advised;

    private final Class<?> targetClass;

    CglibAopProxy(AdvisedSupport advised) {
        this.advised = advised;
        this.targetClass = advised.getTargetClass();
    }

    @Override
    public Object getProxy() {
        return this.getProxy(null);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Enhancer.create(targetClass, this);
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, this.targetClass);
        ProxyMethodInvocation methodInvocation = new CglibMethodInvocation(obj, this.advised.getTarget(), method, args, this.targetClass, chain, proxy);
        return methodInvocation.proceed();
    }
}
