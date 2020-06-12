package com.dragon.springframework.context.context.support;

import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.beans.config.ConfigurableListableBeanFactory;
import com.dragon.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.dragon.springframework.beans.support.BeanDefinitionRegistry;

/**
 * 通用ApplicationContext实现，它具有单个内部{@link DefaultListableBeanFactory}
 * 实例，并且不采用特定的bean定义格式；实现了{@link BeanDefinitionRegistry}接口，
 * 以便允许将任何bean定义读取器应用于该接口。
 *
 * @author SuccessZhang
 * @date 2020/06/10
 */
public class GenericApplicationContext extends AbstractApplicationContext implements BeanDefinitionRegistry {

    private final DefaultListableBeanFactory beanFactory;

    public GenericApplicationContext() {
        this.beanFactory = new DefaultListableBeanFactory();
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws Exception {
        this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) throws Exception {
        this.beanFactory.removeBeanDefinition(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws Exception {
        return this.beanFactory.getBeanDefinition(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanFactory.getBeanDefinitionNames();
    }

    @Override
    public int getBeanDefinitionCount() {
        return this.beanFactory.getBeanDefinitionCount();
    }

    @Override
    public final ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException {
        return this.beanFactory;
    }
}
