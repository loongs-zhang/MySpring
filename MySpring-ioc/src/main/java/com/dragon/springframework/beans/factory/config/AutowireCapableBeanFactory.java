package com.dragon.springframework.beans.factory.config;

import com.dragon.springframework.beans.config.BeanPostProcessor;
import com.dragon.springframework.beans.factory.BeanFactory;
import com.dragon.springframework.beans.factory.DisposableBean;

/**
 * {@link BeanFactory}接口的扩展，由那些能够自动装配的、
 * 想为现有bean实例公开此功能的bean工厂实现。
 *
 * @author SuccessZhang
 * @date 2020/06/12
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 完全创建给定类的新bean实例。执行Bean的完全初始化，
     * 包括所有适用的{@link BeanPostProcessor}。
     * 注意：这用于创建一个新实例，填充带注释的字段和方法，
     * 以及应用所有标准的bean初始化回调。
     */
    <T> T createBean(Class<T> beanClass) throws Exception;

    /**
     * 初始化给定的原始bean，应用工厂回调例如{@code setBeanName}
     * 和{@code setBeanFactory}，还应用所有bean后处理器（包括
     * 可能包装给定原始bean的处理器）。
     */
    Object initializeBean(Object existingBean, String beanName) throws Exception;

    /**
     * 将{@link BeanPostProcessor}应用于给定的现有bean实例，
     * 调用其{@code postProcessBeforeInitialization}方法。
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws Exception;

    /**
     * 将{@link BeanPostProcessor}应用于给定的现有bean实例，
     * 调用其{@code postProcessAfterInitialization}方法。
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws Exception;

    /**
     * 销毁给定的bean实例（通常来自{@link #createBean}），
     * 应用{@link DisposableBean}协定以及已注册的
     * {@link DestructionAwareBeanPostProcessor}，
     * 应该捕获并销毁在销毁过程中出现的任何异常，
     * 而不是传播给此方法的调用者。
     */
    void destroyBean(Object existingBean);
}
