package com.dragon.springframework.beans.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * BeanDefinition描述了一个bean实例，该实例具有属性值，
 * 构造函数参数值以及其他信息。在原生Spring中BeanDefinition是一个接口，
 * 在这里作者为了便于实现进行了简化（主要参考了AbstractBeanDefinition）。
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
@Data
@Builder
@AllArgsConstructor
public class BeanDefinition {

    private boolean lazyInit;

    private final String scope;

    private final String factoryBeanName;

    private final String beanClassName;

    private final Class<?> beanClass;

    private final boolean autowire;

    private final String initMethodName;

    private final String destroyMethodName;

    public BeanDefinition(String scope, String factoryBeanName, String beanClassName, Class<?> beanClass, boolean autowire, String initMethodName, String destroyMethodName) {
        this.lazyInit = false;
        this.scope = scope;
        this.factoryBeanName = factoryBeanName;
        this.beanClassName = beanClassName;
        this.beanClass = beanClass;
        this.autowire = autowire;
        this.initMethodName = initMethodName;
        this.destroyMethodName = destroyMethodName;
    }

    public boolean isSingleton() {
        return ConfigurableBeanFactory.SCOPE_SINGLETON.equals(scope);
    }

    public boolean isPrototype() {
        return ConfigurableBeanFactory.SCOPE_PROTOTYPE.equals(scope);
    }
}
