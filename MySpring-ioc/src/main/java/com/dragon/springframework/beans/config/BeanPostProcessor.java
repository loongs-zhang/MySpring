package com.dragon.springframework.beans.config;

/**
 * 允许自定义修改新的bean实例，例如检查标记界面或使用代理包装它们。
 * ApplicationContexts可以在它们的bean定义中自动检测BeanPostProcessor bean，
 * 并将它们应用于随后创建的任何bean。普通bean工厂允许以编程方式注册
 * 后处理器，适用于通过该工厂创建的所有bean。
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
public interface BeanPostProcessor {

    /**
     * 在bean初始化回调之前将此BeanPostProcessor应用于给定的新bean实例，
     * 此时该bean的所有属性已经填充完成，返回的bean实例可能是原始实例
     * 的包装，默认实现按原样返回给定的bean。
     */
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

    /**
     * 在bean初始化回调之后将此BeanPostProcessor应用于给定的新bean实例，
     * 此时该bean的所有属性已经填充完成，返回的bean实例可能是原始实例的
     * 包装，默认实现按原样返回给定的bean。
     */
    default Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }
}
