package com.dragon.springframework.beans.config;

import com.dragon.springframework.beans.factory.BeanFactory;

/**
 * 除了{@link BeanFactory}接口中的bean factory客户端方法之外，
 * 还提供了用于配置bean factory的接口。此扩展接口仅用于框架内部即插即用，
 * 并允许对bean工厂配置方法的特殊访问，不适合在常规应用程序中使用。
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
public interface ConfigurableBeanFactory extends SingletonBeanRegistry, BeanFactory {

    /**
     * 标准单例作用域的作用域标识符。
     */
    String SCOPE_SINGLETON = "singleton";

    /**
     * 标准多例范围的范围标识符。
     */
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加一个新的BeanPostProcessor，它将应用于该工厂创建的bean。
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁该工厂中的所有单例bean，包括已注册的DisposableBean。
     * 在工厂关闭时被调用，应该捕获并销毁在销毁过程中出现的任何异常，
     * 而不是传播给此方法的调用者。
     */
    void destroySingletons();

}
