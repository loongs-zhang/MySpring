package com.dragon.springframework.aop.aspectj;

import com.dragon.springframework.aop.intercept.MethodInterceptor;
import com.dragon.springframework.aop.intercept.MethodInvocation;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 包装了返回后通知的Spring AOP通知。
 *
 * @author SuccessZhang
 * @date 2020/07/02
 */
@SuppressWarnings("unused")
@NoArgsConstructor
public class AspectJAfterReturningAdvice extends AbstractAspectJAdvice
        implements MethodInterceptor, Serializable {

    public AspectJAfterReturningAdvice(Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder) {
        super(declaringClass, aspect, methodName, pointcut, declarationOrder, AdviceType.afterReturning);
    }

    public AspectJAfterReturningAdvice(String returningName, Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder) {
        super(returningName, declaringClass, aspect, methodName, pointcut, declarationOrder, AdviceType.afterReturning);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object returnValue = invocation.proceed();
        super.invokeAdviceMethod(invocation, returnValue, null);
        return returnValue;
    }
}
