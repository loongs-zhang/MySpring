package com.dragon.springframework.beans.factory;

/**
 * 用于访问Spring bean容器的根接口。
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
