package com.dragon.springframework.aop.aspectj;

import com.dragon.springframework.aop.intercept.MethodInterceptor;
import com.dragon.springframework.aop.intercept.MethodInvocation;
import lombok.NoArgsConstructor;

/**
 * @author SuccessZhang
 * @date 2020/07/02
 */
@NoArgsConstructor
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice implements MethodInterceptor {

    public AspectJAfterThrowingAdvice(Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder) {
        super(declaringClass, aspect, methodName, pointcut, declarationOrder, AdviceType.afterThrowing);
    }

    public AspectJAfterThrowingAdvice(Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder, String throwingName) {
        super(declaringClass, aspect, methodName, pointcut, declarationOrder, AdviceType.afterThrowing, throwingName);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (Throwable t) {
            super.invokeAdviceMethod(invocation, null, t.getCause());
            throw t;
        }
    }
}
