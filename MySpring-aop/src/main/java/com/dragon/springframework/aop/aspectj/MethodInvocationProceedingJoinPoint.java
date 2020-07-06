package com.dragon.springframework.aop.aspectj;

import com.dragon.springframework.aop.ProxyMethodInvocation;
import com.dragon.springframework.aop.intercept.ProceedingJoinPoint;
import lombok.Data;

/**
 * AspectJ{@link ProceedingJoinPoint}接口的实现，
 * 包装了{@link ProxyMethodInvocation}。
 * 注意：{@link #getThis()}方法返回当前的Spring AOP代理，
 * {@link #getTarget()}方法返回当前Spring AOP目标
 * （没有目标则为null），目标是纯POJO，没有任何通知。
 *
 * @author SuccessZhang
 * @date 2020/07/02
 */
@Data
public class MethodInvocationProceedingJoinPoint implements ProceedingJoinPoint {

    private final ProxyMethodInvocation methodInvocation;

    private Object[] defensiveCopyOfArgs;

    public MethodInvocationProceedingJoinPoint(ProxyMethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
    }

    @Override
    public Object proceed() throws Throwable {
        return this.methodInvocation.invocableClone().proceed();
    }

    @Override
    public Object getThis() {
        return this.methodInvocation.getProxy();
    }

    @Override
    public Object getTarget() {
        return this.methodInvocation.getThis();
    }

    @Override
    public Object[] getArgs() {
        if (this.defensiveCopyOfArgs == null) {
            Object[] argsSource = this.methodInvocation.getArguments();
            this.defensiveCopyOfArgs = new Object[argsSource.length];
            System.arraycopy(argsSource, 0, this.defensiveCopyOfArgs, 0, argsSource.length);
        }
        return this.defensiveCopyOfArgs;
    }

    @Override
    public Object proceed(Object[] args) throws Throwable {
        if (args.length != this.methodInvocation.getArguments().length) {
            throw new IllegalArgumentException("Expecting " +
                    this.methodInvocation.getArguments().length + " arguments to proceed, " +
                    "but was passed " + args.length + " arguments");
        }
        this.methodInvocation.setArguments(args);
        return this.methodInvocation.invocableClone(args).proceed();
    }
}
