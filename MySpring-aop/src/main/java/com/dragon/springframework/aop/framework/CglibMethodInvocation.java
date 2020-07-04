package com.dragon.springframework.aop.framework;

import com.dragon.springframework.core.proxy.cglib.MethodProxy;
import com.sun.istack.internal.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Implementation of AOP Alliance MethodInvocation used by this AOP proxy.
 *
 * @author SuccessZhang
 * @date 2020/07/04
 */
public class CglibMethodInvocation extends ReflectiveMethodInvocation {

    private final MethodProxy methodProxy;

    private final boolean publicMethod;

    public CglibMethodInvocation(Object proxy, @Nullable Object target, Method method,
                                 Object[] arguments, @Nullable Class<?> targetClass,
                                 List<Object> interceptorsAndDynamicMethodMatchers, MethodProxy methodProxy) {
        super(proxy, target, method, arguments, targetClass, interceptorsAndDynamicMethodMatchers);
        this.methodProxy = methodProxy;
        this.publicMethod = Modifier.isPublic(method.getModifiers());
    }

    /**
     * Gives a marginal performance improvement versus using reflection to
     * invoke the target when invoking public methods.
     */
    @Override
    protected Object invokeJoinpoint() throws Throwable {
        if (this.publicMethod) {
            return this.methodProxy.invokeSuper(this.proxy, this.arguments);
        } else {
            return super.invokeJoinpoint();
        }
    }
}