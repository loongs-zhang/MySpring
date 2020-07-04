package com.dragon.springframework.aop.aspectj;

import com.dragon.springframework.aop.intercept.MethodInterceptor;
import com.dragon.springframework.aop.intercept.MethodInvocation;
import lombok.NoArgsConstructor;

/**
 * @author SuccessZhang
 * @date 2020/07/02
 */
@NoArgsConstructor
public class AspectJAfterAdvice extends AbstractAspectJAdvice implements MethodInterceptor {

    public AspectJAfterAdvice(Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder) {
        super(declaringClass, aspect, methodName, pointcut, declarationOrder, AdviceType.after);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } finally {
            super.invokeAdviceMethod(invocation, null, null);
        }
    }
}
