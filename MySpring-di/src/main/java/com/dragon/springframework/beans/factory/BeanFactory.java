package com.dragon.springframework.beans.factory;

import com.dragon.springframework.beans.config.BeanPostProcessor;
import com.dragon.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import com.dragon.springframework.context.ApplicationContextAware;
import com.dragon.springframework.context.ApplicationEventPublisherAware;

/**
 * 用于访问Spring bean容器的根接口。
 * BeanFactory实现应尽可能支持标准Bean生命周期接口，
 * 全套初始化方法及其标准顺序为：
 * 1.{@link BeanNameAware}；
 * 2.{@link BeanClassLoaderAware}；
 * 3.{@link BeanFactoryAware}；
 * 4.EnvironmentAware（未实现）；
 * 5.EmbeddedValueResolverAware（未实现）；
 * 6.ResourceLoaderAware（未实现）；
 * <p>
 * （以下仅适用于在应用程序上下文中运行）
 * 7.{@link ApplicationEventPublisherAware}；
 * 8.MessageSourceAware（未实现）；
 * 9.{@link ApplicationContextAware}；
 * 10.ServletContextAware（未实现）；
 * 11.{@link BeanPostProcessor#postProcessBeforeInitialization}；
 * 12.{@link ApplicationEventPublisherAware}；
 * 13.自定义的初始化方法；
 * 14.{@link BeanPostProcessor#postProcessAfterInitialization}；
 * <p>
 * 在关闭BeanFactory时，适用以下生命周期方法：
 * 1.{@link DestructionAwareBeanPostProcessor#postProcessBeforeDestruction}；
 * 2.DisposableBean{@link DisposableBean}；
 * 3.自定义的销毁方法；
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
public interface BeanFactory {

    /**
     * 转义符，将其与由FactoryBean创建的bean区别开来。
     * 使用bean的名字检索FactoryBean得到的对象是工厂生成的对象，
     * 如果需要得到工厂本身，则需要转义。
     */
    String FACTORY_BEAN_PREFIX = "&";

    /**
     * 根据beanName从IOC中获取bean。
     */
    Object getBean(String beanName) throws Exception;

    /**
     * 根据beanName和args从IOC中获取bean。
     */
    Object getBean(String beanName, Object... args) throws Exception;

    /**
     * 根据beanType从IOC中获取bean。
     */
    <T> T getBean(Class<T> requiredType) throws Exception;

    /**
     * 根据beanType和args从IOC中获取bean。
     */
    <T> T getBean(Class<T> requiredType, Object... args) throws Exception;

    /**
     * 根据beanName和beanType从IOC中获取bean。
     */
    <T> T getBean(String beanName, Class<T> requiredType) throws Exception;

    /**
     * 根据beanName、beanType和args从IOC中获取bean。
     */
    <T> T getBean(String beanName, Class<T> requiredType, Object... args) throws Exception;

    /**
     * 看看是否在IOC容器有这个名字的bean。
     */
    boolean containsBean(String name);

    /**
     * 根据bean名字得到bean实例，并判断这个bean是不是单例。
     */
    boolean isSingleton(String name) throws Exception;

    /**
     * 根据bean名字得到bean实例，并判断这个bean是不是多例。
     */
    boolean isPrototype(String name) throws Exception;

    /**
     * 获取指定名称bean的Class类型。
     */
    Class<?> getType(String name) throws Exception;
}
