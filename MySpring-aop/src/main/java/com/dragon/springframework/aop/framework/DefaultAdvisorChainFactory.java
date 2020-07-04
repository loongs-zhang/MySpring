package com.dragon.springframework.aop.framework;

import com.dragon.springframework.aop.Advisor;
import com.dragon.springframework.aop.ClassFilter;
import com.dragon.springframework.aop.MethodMatcher;
import com.dragon.springframework.aop.Pointcut;
import com.dragon.springframework.aop.aspectj.AspectJPointcutAdvisor;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/07/03
 */
public class DefaultAdvisorChainFactory {
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(AdvisedSupport advisedSupport, Method method, Class<?> targetClass) {
        List<Object> advices = new LinkedList<>();
        for (Advisor advisor : advisedSupport.getAdvisors()) {
            AspectJPointcutAdvisor pointcutAdvisor = (AspectJPointcutAdvisor) advisor;
            Pointcut pointcut = pointcutAdvisor.getPointcut();
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