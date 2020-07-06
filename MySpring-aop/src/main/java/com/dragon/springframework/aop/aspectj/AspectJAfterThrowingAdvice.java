package com.dragon.springframework.aop.aspectj;

import com.dragon.springframework.aop.intercept.MethodInterceptor;
import com.dragon.springframework.aop.intercept.MethodInvocation;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 包装了抛出异常后通知的Spring AOP通知。
 *
 * @author SuccessZhang
 * @date 2020/07/02
 */
@SuppressWarnings("unused")
@NoArgsConstructor
public class AspectJAfterThrowingAdvice extends AbstractAspectJAdvice
        implements MethodInterceptor, Serializable {

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
