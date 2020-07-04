package com.dragon.springframework.aop.aspectj;

import com.dragon.springframework.aop.Advice;
import com.dragon.springframework.aop.ClassFilter;
import com.dragon.springframework.aop.MethodMatcher;
import com.dragon.springframework.aop.support.ExpressionPointcut;
import com.dragon.springframework.aop.support.expression.ExpressionMatcher;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SuccessZhang
 * @date 2020/07/02
 */
public class AspectJExpressionPointcut implements ExpressionPointcut, ClassFilter, MethodMatcher, Serializable {

    private final ExpressionMatcher matcher;

    private static final String EXECUTION = "execution";

    @Getter
    @Setter
    private String id;

    @Setter
    private String expression;

    /**
     * 原生方法对应的拦截器链缓存
     */
    private Map<Method, Set<Advice>> shadowMatchCache = new ConcurrentHashMap<>(32);

    public AspectJExpressionPointcut(String id, String expression) {
        this.id = id;
        //去除execution()
        expression = expression.replaceAll(EXECUTION + "\\(", "");
        expression = expression.substring(0, expression.length() - 1);
        this.expression = expression;
        matcher = new ExpressionMatcher(expression);
    }

    private Set<Advice> getAdvices(Method target) {
        for (Map.Entry<Method, Set<Advice>> entry : shadowMatchCache.entrySet()) {
            Method method = entry.getKey();
            Class<?> impl = method.getDeclaringClass();
            List<Class<?>> interfaces = Arrays.asList(impl.getInterfaces());
            Class<?> theInterface = target.getDeclaringClass();
            if (theInterface.isAssignableFrom(impl) || interfaces.contains(theInterface)) {
                if (method.getName().equals(target.getName()) &&
                        method.getReturnType().equals(target.getReturnType()) &&
                        equalParamTypes(method.getParameterTypes(), target.getParameterTypes())) {
                    return entry.getValue();
                }
            }
        }
        return new LinkedHashSet<>();
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

    private boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
        if (params1.length == params2.length) {
            for (int i = 0; i < params1.length; i++) {
                if (params1[i] != params2[i]) {
                    return false;
                }
            }
            return true;
        }
        return false;
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
        return this.matcher;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this.matcher;
    }
}
