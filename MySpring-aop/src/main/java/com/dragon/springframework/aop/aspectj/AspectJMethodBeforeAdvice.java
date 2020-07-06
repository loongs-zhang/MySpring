package com.dragon.springframework.aop.aspectj;

import com.dragon.springframework.aop.intercept.MethodInterceptor;
import com.dragon.springframework.aop.intercept.MethodInvocation;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 包装了在方法之前通知的Spring AOP通知。
 *
 * @author SuccessZhang
 * @date 2020/07/02
 */
@SuppressWarnings("unused")
@NoArgsConstructor
public class AspectJMethodBeforeAdvice extends AbstractAspectJAdvice
        implements MethodInterceptor, Serializable {

    public AspectJMethodBeforeAdvice(Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder) {
        super(declaringClass, aspect, methodName, pointcut, declarationOrder, AdviceType.before);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        super.invokeAdviceMethod(invocation, null, null);
        return invocation.proceed();
    }
}
