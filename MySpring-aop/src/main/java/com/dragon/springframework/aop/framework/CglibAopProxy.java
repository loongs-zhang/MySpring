package com.dragon.springframework.aop.framework;

import com.dragon.springframework.aop.ProxyMethodInvocation;
import com.dragon.springframework.core.proxy.cglib.Enhancer;
import com.dragon.springframework.core.proxy.cglib.MethodInterceptor;
import com.dragon.springframework.core.proxy.cglib.MethodProxy;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/07/03
 */
@RequiredArgsConstructor
final class CglibAopProxy implements AopProxy, MethodInterceptor {

    private final AdvisedSupport advised;

    @Override
    public Object getProxy() {
        return this.getProxy(null);
    }

    @Override
    public Object getProxy(ClassLoader classLoader) {
        return Enhancer.create(this.advised.getTargetClass(), this);
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Class<?> targetClass = this.advised.getTargetClass();
        List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
        ProxyMethodInvocation methodInvocation = new CglibMethodInvocation(obj, this.advised.getTarget(), method, args, targetClass, chain, proxy);
        return methodInvocation.proceed();
    }
}
