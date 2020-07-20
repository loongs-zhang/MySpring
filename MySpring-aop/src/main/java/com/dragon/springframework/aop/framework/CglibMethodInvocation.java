package com.dragon.springframework.aop.framework;

import com.dragon.springframework.core.proxy.cglib.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 此AOP代理使用FastClass而不是反射调用，性能得到了一定的提高。
 *
 * @author SuccessZhang
 * @date 2020/07/04
 */
public class CglibMethodInvocation extends ReflectiveMethodInvocation {

    private final MethodProxy methodProxy;

    public CglibMethodInvocation(Object proxy, Object target, Method method,
                                 Object[] arguments, Class<?> targetClass,
                                 List<Object> interceptorsAndDynamicMethodMatchers, MethodProxy methodProxy) {
        super(proxy, target, method, arguments, targetClass, interceptorsAndDynamicMethodMatchers);
        this.methodProxy = methodProxy;
    }

    @Override
    protected Object invokeJoinPoint() throws Throwable {
        return this.methodProxy.invokeSuper(this.proxy, this.arguments);
    }
}