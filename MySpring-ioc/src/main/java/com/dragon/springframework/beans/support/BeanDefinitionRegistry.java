package com.dragon.springframework.beans.support;

import com.dragon.springframework.beans.config.BeanDefinition;

/**
 * 包含bean定义的注册表的接口，这是Spring的bean工厂中唯一封装
 * 注册bean定义的接口。标准BeanFactory接口仅涵盖对完全配置的
 * 工厂实例的访问。Spring的bean定义阅读器希望可以在此接口的实现上
 * 进行工作。Spring核心中的已知实现者是DefaultListableBeanFactory
 * 和GenericApplicationContext。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
public interface BeanDefinitionRegistry {

    /**
     * 在此注册表中注册新的bean定义。
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws Exception;

    /**
     * 删除给定名称的Bean定义。
     */
    void removeBeanDefinition(String beanName) throws Exception;

    /**
     * 返回给定bean名称的Bean定义。
     */
    BeanDefinition getBeanDefinition(String beanName) throws Exception;

    /**
     * 检查此注册表是否包含具有给定名称的bean定义。
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回此注册表中定义的所有bean的名称。
     */
    String[] getBeanDefinitionNames();

    /**
     * 返回注册表中bean定义的数量。
     */
    int getBeanDefinitionCount();

}
