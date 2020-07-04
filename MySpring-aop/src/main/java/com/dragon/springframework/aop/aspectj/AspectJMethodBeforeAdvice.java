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
public class AspectJMethodBeforeAdvice extends AbstractAspectJAdvice implements MethodInterceptor {

    private JoinPoint joinPoint;

    public AspectJMethodBeforeAdvice(Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder) {
        super(declaringClass, aspect, methodName, pointcut, declarationOrder, AdviceType.before);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        this.joinPoint = invocation;
        before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }

    private void before(Method method, Object[] arguments, Object target) throws Throwable {
        super.invokeAdviceMethod(joinPoint, null, null);
    }
}
