package com.dragon.springframework.aop.aspectj;

import com.dragon.springframework.aop.intercept.JoinPoint;
import com.dragon.springframework.aop.intercept.MethodInterceptor;
import com.dragon.springframework.aop.intercept.MethodInvocation;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * @author SuccessZhang
 * @date 2020/07/02
 */
@NoArgsConstructor
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice implements MethodInterceptor {

    private JoinPoint joinPoint;

    public AspectJAfterReturningAdvice(Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder) {
        super(declaringClass, aspect, methodName, pointcut, declarationOrder, AdviceType.afterReturning);
    }

    public AspectJAfterReturningAdvice(String returningName, Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder) {
        super(returningName, declaringClass, aspect, methodName, pointcut, declarationOrder, AdviceType.afterReturning);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        joinPoint = invocation;
        Object returnValue = invocation.proceed();
        afterReturning(returnValue, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return returnValue;
    }

    private void afterReturning(Object returnValue, Method method, Object[] arguments, Object target) throws Throwable {
        super.invokeAdviceMethod(joinPoint, returnValue, null);
    }
}
