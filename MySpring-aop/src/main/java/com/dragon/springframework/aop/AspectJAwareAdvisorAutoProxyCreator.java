package com.dragon.springframework.aop;

import com.dragon.springframework.beans.config.BeanPostProcessor;
import com.dragon.springframework.context.ApplicationContext;

/**
 * @author SuccessZhang
 * @date 2020/06/30
 */
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public AspectJAwareAdvisorAutoProxyCreator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return null;
    }
}
