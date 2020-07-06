package com.dragon.springframework.aop.aspectj;

import com.dragon.springframework.aop.Advice;
import com.dragon.springframework.aop.ClassFilter;
import com.dragon.springframework.aop.MethodMatcher;
import com.dragon.springframework.aop.Pointcut;
import com.dragon.springframework.aop.support.ExpressionPointcut;
import com.dragon.springframework.aop.support.expression.DefaultClassMatcher;
import com.dragon.springframework.aop.support.expression.DefaultMethodMatcher;
import com.dragon.springframework.aop.support.expression.ExpressionMatcher;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@link Pointcut}的实现，使用AspectJ解析切入点表达式
 * （切入点表达式值是AspectJ表达式，另外，作者没有引入
 * AspectJ依赖，而是自己另写类完成解析）。由于通过代理模型处理，
 * 因此仅支持方法执行切入点。
 *
 * @author SuccessZhang
 * @date 2020/07/02
 */
public class AspectJExpressionPointcut implements ExpressionPointcut, ClassFilter, MethodMatcher, Serializable {

    private final ExpressionMatcher matcher = ExpressionMatcher.getInstance();

    private static final String EXECUTION = "execution";

    @Getter
    @Setter
    private String id;

    @Setter
    private String expression;

    /**
     * 原生方法对应的拦截器链缓存。
     */
    private Map<Method, Set<Advice>> shadowMatchCache = new ConcurrentHashMap<>(32);

    public AspectJExpressionPointcut(String id, String expression) {
        this.id = id;
        //去除execution()
        expression = expression.replaceAll(EXECUTION + "\\(", "");
        expression = expression.substring(0, expression.length() - 1);
        this.expression = expression;
    }

    public boolean hasAdvice(Class<?> beanClass) {
        Set<Advice> result = new LinkedHashSet<>();
        for (Method method : beanClass.getDeclaredMethods()) {
            Set<Advice> advices = shadowMatchCache.get(method);
            if (advices == null) {
                continue;
            }
            result.addAll(advices);
        }
        return !result.isEmpty();
    }

    public boolean hasAdvice(Method method) {
        Set<Advice> advices = shadowMatchCache.get(method);
        return advices != null && !advices.isEmpty();
    }

    public Set<Advice> addAdvice(Method method, Advice advice) {
        Set<Advice> advices = shadowMatchCache.computeIfAbsent(method, k -> new LinkedHashSet<>());
        advices.add(advice);
        return advices;
    }

    @Override
    public String getExpression() {
        return this.expression;
    }

    @Override
    public boolean matches(Class target, String expression) {
        return matcher.matches(target, expression);
    }

    @Override
    public boolean matches(Class target) {
        return matcher.matches(target, this.expression);
    }

    @Override
    public boolean matches(Method target, String expression) {
        return matcher.matches(target, expression);
    }

    @Override
    public boolean matches(Method target) {
        return matcher.matches(target, this.expression);
    }

    @Override
    public ClassFilter getClassFilter() {
        DefaultClassMatcher classMatcher = this.matcher.getClassMatcher();
        classMatcher.setExpression(this.expression);
        return classMatcher;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        DefaultMethodMatcher methodMatcher = this.matcher.getMethodMatcher();
        methodMatcher.setExpression(this.expression);
        return methodMatcher;
    }
}
