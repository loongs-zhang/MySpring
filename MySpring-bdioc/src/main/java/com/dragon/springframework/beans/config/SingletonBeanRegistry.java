package com.dragon.springframework.beans.config;

/**
 * 规范共享bean实例定义注册表的接口。
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
public interface SingletonBeanRegistry {

    /**
     * 在给定的bean名称下，将给定的现有对象注册为单例。
     * 该给定bean实例应该被完全初始化；注册表将不会执行任何初始化回调，
     * 给定的实例也不会接收任何破坏回调。
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 返回以给定名称注册的（原始）单例对象。
     * 注意此查找方法不知道FactoryBean前缀或别名，
     * 因此在获取单例实例之前需要首先解析规范化的bean名称。
     */
    Object getSingleton(String beanName);

    /**
     * 检查此注册表是否包含具有给定名称的单例实例。
     * 仅检查已实例化的单例；对于尚未实例化的单例bean定义，不返回true。
     * 要检查bean工厂是否包含具有给定名称的bean定义，
     * 请使用ListableBeanFactory的{@code containsBeanDefinition}。
     * 注意此查找方法不知道FactoryBean前缀或别名，
     * 因此在获取单例实例之前需要首先解析规范化的bean名称。
     */
    boolean containsSingleton(String beanName);

    /**
     * 返回在此注册表中注册的单例bean的名称。
     * 仅检查已实例化的单例；对于尚未实例化的单例bean定义，不返回名称。
     */
    String[] getSingletonNames();

    /**
     * 返回在此注册表中注册的单例bean的数量。
     * 仅检查已实例化的单例；不计算尚未实例化的单例bean定义。
     */
    int getSingletonCount();
}
