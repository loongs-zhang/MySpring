package com.dragon.springframework.aop.framework;

import com.dragon.springframework.core.proxy.cglib.MethodProxy;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 此AOP代理使用的AOP Alliance MethodInvocation的实现。
 *
 * @author SuccessZhang
 * @date 2020/07/04
 */
public class CglibMethodInvocation extends ReflectiveMethodInvocation {

    private final MethodProxy methodProxy;

    public CglibMethodInvocation(Object proxy, @Nullable Object target, Method method,
                                 Object[] arguments, @Nullable Class<?> targetClass,
                                 List<Object> interceptorsAndDynamicMethodMatchers, MethodProxy methodProxy) {
        super(proxy, target, method, arguments, targetClass, interceptorsAndDynamicMethodMatchers);
        this.methodProxy = methodProxy;
    }

    /**
     * 使用FastClass而不是反射调用，性能得到了一定的提高。
     */
    @Override
    protected Object invokeJoinPoint() throws Throwable {
        return this.methodProxy.invokeSuper(this.proxy, this.arguments);
    }
}