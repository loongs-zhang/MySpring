package com.dragon.springframework.aop.aspectj;

import com.dragon.springframework.aop.Advice;
import com.dragon.springframework.aop.intercept.JoinPoint;
import com.dragon.springframework.core.Ordered;
import com.dragon.springframework.core.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * before、after、around、afterReturning、afterThrowing通知的共同父类，
 * 对应xml中<aop:before/>等标签的内容。
 *
 * @author SuccessZhang
 * @date 2020/07/02
 */
@NoArgsConstructor
public abstract class AbstractAspectJAdvice implements Advice, Ordered {

    protected Class<?> declaringClass;

    protected String methodName;

    protected Method aspectJAdviceMethod;

    protected AspectJExpressionPointcut pointcut;

    /**
     * The aspect (ref bean) in which this advice was defined
     * (used when determining advice precedence so that we can determine
     * whether two pieces of advice come from the same aspect).
     */
    protected Object aspect;

    /**
     * The order of declaration of this advice within the aspect.
     */
    protected int declarationOrder;

    /**
     * Non-null if after throwing advice binds the thrown value
     */
    @Setter
    protected String throwingName;

    /**
     * Non-null if after returning advice binds the return value
     */
    @Setter
    protected String returningName;

    @Getter
    protected AdviceType adviceType;

    public AbstractAspectJAdvice(Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder, AdviceType adviceType, String throwingName) {
        this(declaringClass, aspect, methodName, pointcut, declarationOrder, adviceType);
        this.throwingName = throwingName;
    }

    public AbstractAspectJAdvice(String returningName, Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder, AdviceType adviceType) {
        this(declaringClass, aspect, methodName, pointcut, declarationOrder, adviceType);
        this.returningName = returningName;
    }

    public AbstractAspectJAdvice(Class<?> declaringClass, Object aspect, String methodName, AspectJExpressionPointcut pointcut, int declarationOrder, AdviceType adviceType) {
        this.declaringClass = declaringClass;
        this.aspect = aspect;
        this.methodName = methodName;
        this.aspectJAdviceMethod = getAspectJAdviceMethod(declaringClass, methodName);
        this.pointcut = pointcut;
        this.declarationOrder = declarationOrder;
        this.adviceType = adviceType;
    }

    private Method getAspectJAdviceMethod(Class<?> declaringClass, String methodName) {
        for (Method method : declaringClass.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new RuntimeException("advice method not found !");
    }

    @Override
    public int getOrder() {
        return this.declarationOrder;
    }

    /**
     * Invoke the advice method.
     *
     * @param joinPoint   the JoinPointMatch that matched this execution join point
     * @param returnValue the return value from the method execution (may be null)
     * @param ex          the exception thrown by the method execution (may be null)
     * @return the invocation result
     * @throws Throwable in case of invocation failure
     */
    protected Object invokeAdviceMethod(JoinPoint joinPoint, Object returnValue, Throwable ex) throws Throwable {
        this.aspectJAdviceMethod.setAccessible(true);
        Parameter[] parameters = this.aspectJAdviceMethod.getParameters();
        if (parameters.length == 0) {
            return this.aspectJAdviceMethod.invoke(this.aspect);
        }
        Object[] params = new Object[parameters.length];
        for (int i = 0; i < params.length; i++) {
            Class<?> type = parameters[i].getType();
            if (JoinPoint.class.isAssignableFrom(type)) {
                params[i] = joinPoint;
            } else if (Throwable.class.isAssignableFrom(type)) {
                String throwingName = parameters[i].getName();
                if (StringUtils.isEmpty(this.throwingName) ||
                        this.throwingName.equals(throwingName)) {
                    params[i] = ex;
                }
            } else {
                String returningName = parameters[i].getName();
                if (StringUtils.isEmpty(this.returningName) ||
                        this.returningName.equals(returningName)) {
                    params[i] = returnValue;
                }
            }
        }
        return this.aspectJAdviceMethod.invoke(this.aspect, params);
    }

    public enum AdviceType {

        /**
         * 前置通知
         */
        before(1),

        /**
         * 后置通知
         */
        after(2),

        /**
         * 环绕通知
         */
        around(3),

        /**
         * 返回通知
         */
        afterReturning(4),

        /**
         * 异常通知
         */
        afterThrowing(5);

        private final int priority;

        AdviceType(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }

        private static final Map<String, AdviceType> MAP = new HashMap<>(AdviceType.values().length);

        static {
            for (AdviceType value : AdviceType.values()) {
                MAP.put(value.name(), value);
            }
        }

        public static AdviceType getAdviceType(String name) {
            return MAP.get(name);
        }
    }
}
