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

    /**
     * 切面Bean Class类型。
     */
    protected Class<?> declaringClass;

    /**
     * 回调通知方法的名称。
     */
    protected String methodName;

    /**
     * 回调的通知方法。
     */
    protected Method aspectJAdviceMethod;

    protected AspectJExpressionPointcut pointcut;

    /**
     * 切面Bean实例。
     */
    protected Object aspect;

    /**
     * 该通知在切面中的声明顺序。
     */
    protected int declarationOrder;

    /**
     * 绑定回调时异常参数的名称。
     */
    @Setter
    protected String throwingName;

    /**
     * 绑定回调时返回值的名称。
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
     * 回调切面Bean实例中的通知方法。
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
            } else if (type.isInstance(returnValue)) {
                String returningName = parameters[i].getName();
                if (StringUtils.isEmpty(this.returningName) ||
                        this.returningName.equals(returningName)) {
                    params[i] = returnValue;
                }
            }
        }
        return this.aspectJAdviceMethod.invoke(this.aspect, params);
    }

    /**
     * 通知类型。
     */
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

        /**
         * 通知的优先级，数值越小优先级越高。
         */
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
