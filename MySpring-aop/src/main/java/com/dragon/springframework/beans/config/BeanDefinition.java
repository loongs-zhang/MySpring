package com.dragon.springframework.beans.config;

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
public class BeanDefinition {

    /**
     * 是否懒加载。
     */
    private boolean lazyInit;

    /**
     * Bean的作用域范围，有单例和多例。
     */
    private final String scope;

    /**
     * 存储在BeanFactory中的Bean名称。
     */
    private final String factoryBeanName;

    /**
     * Bean的Class全量名。
     */
    private final String beanClassName;

    /**
     * Bean的Class。
     */
    private final Class<?> beanClass;

    /**
     * 是否自动装配属性。
     */
    private final boolean autowire;

    /**
     * 调用构造方法的参数。
     */
    private final Object[] initArguments;

    /**
     * 自定义的初始化方法。
     */
    private final String initMethodName;

    /**
     * 自定义的销毁方法。
     */
    private final String destroyMethodName;

    public boolean isSingleton() {
        return ConfigurableBeanFactory.SCOPE_SINGLETON.equals(scope);
    }

    public boolean isPrototype() {
        return ConfigurableBeanFactory.SCOPE_PROTOTYPE.equals(scope);
    }
}
