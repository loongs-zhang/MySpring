package com.dragon.springframework.context.context.support;

import com.dragon.springframework.beans.config.BeanPostProcessor;
import com.dragon.springframework.beans.factory.Aware;
import com.dragon.springframework.context.ApplicationContext;
import com.dragon.springframework.context.ApplicationContextAware;
import com.dragon.springframework.context.ApplicationEventPublisherAware;

/**
 * {@link BeanPostProcessor}实现，将ApplicationContext传递给实现
 * {@link ApplicationEventPublisherAware}和{@link ApplicationContextAware}接口
 * 的类。按上面提到的顺序满足实现的接口，应用程序上下文将自动在其基础bean工厂中
 * 注册它，应用程序不直接使用它。
 *
 * @author SuccessZhang
 * @date 2020/06/12
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        invokeAwareInterfaces(bean);
        return bean;
    }

    private void invokeAwareInterfaces(Object bean) throws Exception {
        if (bean instanceof Aware) {
            if (bean instanceof ApplicationEventPublisherAware) {
                ((ApplicationEventPublisherAware) bean).setApplicationEventPublisher(this.applicationContext);
            }
            if (bean instanceof ApplicationContextAware) {
                ((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
            }
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }
}
