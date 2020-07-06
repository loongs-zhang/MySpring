package com.dragon.springframework.aop.aspectj;

import com.dragon.springframework.aop.ProxyMethodInvocation;
import com.dragon.springframework.aop.intercept.MethodInterceptor;
import com.dragon.springframework.aop.intercept.MethodInvocation;
import com.dragon.springframework.aop.intercept.ProceedingJoinPoint;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 包装了通知方法的Spring AOP环绕通知，
 * 暴露了ProceedingJoinPoint。
 *
 * @author SuccessZhang
 * @date 2020/07/02
 */
@SuppressWarnings("unused")
@NoArgsConstructor
public class AspectJAroundAdvice extends AbstractAspectJAdvice
        implements MethodInterceptor, Serializable {

    public AspectJAroundAdvice(Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder) {
        super(declaringClass, aspect, methodName, pointcut, declarationOrder, AdviceType.around);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        ProceedingJoinPoint proceedingJoinPoint = new MethodInvocationProceedingJoinPoint((ProxyMethodInvocation) invocation);
        return super.invokeAdviceMethod(proceedingJoinPoint, null, null);
    }
}
