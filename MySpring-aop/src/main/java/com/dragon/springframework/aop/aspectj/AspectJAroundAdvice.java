package com.dragon.springframework.aop.aspectj;

import com.dragon.springframework.aop.ProxyMethodInvocation;
import com.dragon.springframework.aop.intercept.MethodInterceptor;
import com.dragon.springframework.aop.intercept.MethodInvocation;
import lombok.NoArgsConstructor;

/**
 * @author SuccessZhang
 * @date 2020/07/02
 */
@NoArgsConstructor
public class AspectJAroundAdvice extends AbstractAspectJAdvice implements MethodInterceptor {

    public AspectJAroundAdvice(Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder) {
        super(declaringClass, aspect, methodName, pointcut, declarationOrder, AdviceType.around);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        MethodInvocationProceedingJoinPoint proceedingJoinPoint = new MethodInvocationProceedingJoinPoint((ProxyMethodInvocation) invocation);
        return super.invokeAdviceMethod(proceedingJoinPoint, null, null);
    }
}
