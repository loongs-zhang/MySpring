package com.dragon.springframework.aop.framework.autoproxy;

import com.dragon.springframework.aop.Advisor;
import com.dragon.springframework.aop.aspectj.AbstractAspectJAdvice;
import com.dragon.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.dragon.springframework.aop.aspectj.AspectJPointcutAdvisor;
import com.dragon.springframework.aop.framework.ProxyFactory;
import com.dragon.springframework.beans.config.BeanPostProcessor;
import com.dragon.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/06/30
 */
@SuppressWarnings("unused")
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public AspectJAwareAdvisorAutoProxyCreator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        Class<?> targetClass = bean.getClass();
        List<Advisor> advisors = getAdvicesAndAdvisorsForBean(targetClass, beanName);
        if (advisors.isEmpty()) {
            return bean;
        }
        //至少有一个通知，需要创建代理对象
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisors(advisors);
        return proxyFactory.getProxy(targetClass.getClassLoader());
    }

    private List<Advisor> getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName) throws Exception {
        List<Advisor> advisors = new LinkedList<>();
        for (Advisor advisor : applicationContext.getBeansOfType(Advisor.class).values()) {
            AspectJPointcutAdvisor pointcutAdvisor = (AspectJPointcutAdvisor) advisor;
            AspectJExpressionPointcut pointcut = (AspectJExpressionPointcut) pointcutAdvisor.getPointcut();
            for (Method method : beanClass.getDeclaredMethods()) {
                if (pointcut.matches(method)) {
                    pointcut.addAdvice(method, pointcutAdvisor.getAdvice());
                }
            }
            if (pointcut.hasAdvice(beanClass)) {
                advisors.add(advisor);
            }
        }
        return sortAdvisors(advisors);
    }

    private List<Advisor> sortAdvisors(List<Advisor> advisors) {
        advisors.sort(new Comparator<Advisor>() {
            @Override
            public int compare(Advisor o1, Advisor o2) {
                AbstractAspectJAdvice o1Advice = (AbstractAspectJAdvice) o1.getAdvice();
                AbstractAspectJAdvice o2Advice = (AbstractAspectJAdvice) o2.getAdvice();
                String o1Order = o1Advice.getAdviceType().getPriority() + String.valueOf(o1Advice.getOrder());
                String o2Order = o2Advice.getAdviceType().getPriority() + String.valueOf(o2Advice.getOrder());
                return Long.compare(Long.valueOf(o1Order), Long.valueOf(o2Order));
            }
        });
        return advisors;
    }
}
