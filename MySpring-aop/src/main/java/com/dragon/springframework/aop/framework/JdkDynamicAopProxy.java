package com.dragon.springframework.aop.framework;

import com.dragon.springframework.aop.ProxyMethodInvocation;
import com.dragon.springframework.core.proxy.jdk.InvocationHandler;
import com.dragon.springframework.core.proxy.jdk.Proxy;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/07/03
 */
@RequiredArgsConstructor
final class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;

    @Override
    public Object getProxy() {
        return this.getProxy(this.advised.getTargetClass().getClassLoader());
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Proxy.newProxyInstance(classLoader, this.advised.getTargetClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> targetClass = this.advised.getTargetClass();
        List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
        ProxyMethodInvocation methodInvocation = new ReflectiveMethodInvocation(proxy, this.advised.getTarget(), method, args, targetClass, chain);
        return methodInvocation.proceed();
    }
}
