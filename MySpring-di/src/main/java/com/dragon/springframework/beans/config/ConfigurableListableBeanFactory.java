package com.dragon.springframework.beans.config;

import com.dragon.springframework.beans.factory.FactoryBean;
import com.dragon.springframework.beans.factory.ListableBeanFactory;
import com.dragon.springframework.beans.factory.config.AutowireCapableBeanFactory;

import java.util.Iterator;

/**
 * 提供了用于分析和修改bean定义以及预先实例化单例的工具，
 * 仅允许框架内部即插即用。
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 返回指定Bean的注册BeanDefinition，从而允许对其属性值和构造函数
     * 进行访问（可以在Bean工厂后处理期间进行修改）。
     */
    BeanDefinition getBeanDefinition(String beanName) throws Exception;

    /**
     * 返回对此工厂管理的所有bean名称的统一视图。
     */
    Iterator<String> getBeanNamesIterator();

    /**
     * 确保所有非延迟初始单例都实例化，
     * 需要考虑{@link FactoryBean FactoryBeans}，
     * 通常在出厂设置结束时调用。
     */
    void preInstantiateSingletons() throws Exception;
}
