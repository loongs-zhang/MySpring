package com.dragon.springframework.beans.factory;

/**
 * 由{@link BeanFactory}中使用的对象实现的接口，
 * 这些对象本身是单个对象的工厂，用于产生其他对象。
 * 如果bean实现此接口，它将用作对象公开的工厂，
 * 而不是直接用作将自身公开的bean实例。
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
public interface FactoryBean<T> {

    /**
     * 返回此Bean工厂管理的对象的实例。
     */
    T getObject() throws Exception;

    /**
     * 返回此Bean工厂创建的对象的类型。
     */
    Class<?> getObjectType();

    /**
     * Bean工厂创建的对象是否是单态模式。
     */
    default boolean isSingleton() {
        return true;
    }

}