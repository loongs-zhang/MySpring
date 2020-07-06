package com.dragon.springframework.aop.framework;

import com.dragon.springframework.aop.Advisor;
import com.dragon.springframework.aop.ClassFilter;
import com.dragon.springframework.aop.MethodMatcher;
import com.dragon.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.dragon.springframework.aop.aspectj.AspectJPointcutAdvisor;
import com.dragon.springframework.aop.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个{@link AdvisedSupport}对象，
 * 为方法指定通知链、始终重建每条通知链的工厂。
 *
 * @author SuccessZhang
 * @date 2020/07/03
 */
public class DefaultAdvisorChainFactory {

    /**
     * 确定给定Advisor链配置的{@link MethodInterceptor}对象的列表。
     * Spring原生在这里可能返回包含InterceptorAndDynamicMethodMatcher的列表。
     */
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport advisedSupport, Method method, Class<?> targetClass) {
        List<Object> advices = new LinkedList<>();
        for (Advisor advisor : advisedSupport.getAdvisors()) {
            AspectJPointcutAdvisor pointcutAdvisor = (AspectJPointcutAdvisor) advisor;
            AspectJExpressionPointcut pointcut = (AspectJExpressionPointcut) pointcutAdvisor.getPointcut();
            ClassFilter classFilter = pointcut.getClassFilter();
            MethodMatcher methodMatcher = pointcut.getMethodMatcher();
            if (classFilter.matches(targetClass) &&
                    methodMatcher.matches(method)) {
                advices.add(advisor.getAdvice());
            }
        }
        return advices;
    }
}