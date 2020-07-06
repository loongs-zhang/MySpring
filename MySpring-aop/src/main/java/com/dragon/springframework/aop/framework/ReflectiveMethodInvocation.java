package com.dragon.springframework.aop.framework;

import com.dragon.springframework.aop.ProxyMethodInvocation;
import com.dragon.springframework.aop.intercept.MethodInterceptor;
import com.dragon.springframework.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对于专门的MethodInvocation实现是有用的基类。
 * 注意：此类被认为是内部的，不应直接访问。
 * 公开的唯一原因是与现有框架集成，出于其他任何目的，
 * 请改用{@link ProxyMethodInvocation}接口。
 *
 * @author SuccessZhang
 * @date 2020/07/03
 */
public class ReflectiveMethodInvocation implements ProxyMethodInvocation, Cloneable {

    protected final Object proxy;

    protected final Object target;

    protected final Method method;

    protected Object[] arguments;

    private final Class<?> targetClass;

    /**
     * 此调用的用户特定属性映射。
     */
    private Map<String, Object> userAttributes = new HashMap<>(16);

    /**
     * 拦截器列表，在这里Spring原生还可能是InterceptorAndDynamicMethodMatcher。
     */
    protected final List<?> interceptorsAndDynamicMethodMatchers;

    /**
     * 正在调用的当前拦截器的索引。
     */
    private int currentInterceptorIndex = -1;

    public ReflectiveMethodInvocation(
            Object proxy, Object target, Method method, Object[] arguments,
            Class<?> targetClass, List<Object> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.targetClass = targetClass;
        this.method = method;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    @Override
    public Object getProxy() {
        return this.proxy;
    }

    @Override
    public MethodInvocation invocableClone() {
        Object[] cloneArguments = this.arguments;
        if (this.arguments.length > 0) {
            cloneArguments = new Object[this.arguments.length];
            System.arraycopy(this.arguments, 0, cloneArguments, 0, this.arguments.length);
        }
        return invocableClone(cloneArguments);
    }

    @Override
    public MethodInvocation invocableClone(Object... arguments) {
        try {
            ReflectiveMethodInvocation clone = (ReflectiveMethodInvocation) clone();
            clone.arguments = arguments;
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new IllegalStateException(
                    "Should be able to clone object of type [" + getClass() + "]: " + ex);
        }
    }

    @Override
    public void setArguments(Object... arguments) {
        this.arguments = arguments;
    }

    @Override
    public void setUserAttribute(String key, Object value) {
        if (value != null) {
            this.userAttributes.put(key, value);
        } else {
            this.userAttributes.remove(key);
        }
    }

    @Override
    public Object getUserAttribute(String key) {
        return this.userAttributes.get(key);
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Object proceed() throws Throwable {
        //如果Interceptor执行完了，则执行joinPoint
        if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return invokeJoinPoint();
        }
        Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        //尝试执行拦截链
        if (interceptorOrInterceptionAdvice instanceof MethodInterceptor) {
            MethodInterceptor dm = (MethodInterceptor) interceptorOrInterceptionAdvice;
            return dm.invoke(this);
        } else {
            //动态匹配失败，跳过当前Interceptor，调用下一个Interceptor
            return proceed();
        }
    }

    /**
     * 使用反射调用连接点，子类可以重写此方法以使用自定义调用。
     */
    protected Object invokeJoinPoint() throws Throwable {
        return this.method.invoke(this.target, this.arguments);
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    /**
     * Spring原生实现的是org.aopalliance.intercept.Joinpoint接口，
     * 没有定义getTarget()方法，而作者把Spring定义的接口和
     * AspectJ定义的接口合并了，所以会实现这个本来不该实现的方法。
     */
    @Override
    public Object getTarget() {
        return this.target;
    }

    @Override
    public Object[] getArgs() {
        return this.getArguments();
    }
}
