package com.dragon.springframework.beans.factory;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 该BeanFactory可以枚举其所有bean实例，
 * 而不是按客户的要求按名称一一尝试查找bean。
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 检查此bean工厂是否包含具有给定名称的bean定义。
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回与给定类型（包括子类）匹配的bean的名称。
     */
    String[] getBeanNamesForType(Class<?> type);

    /**
     * 根据Factory Beans的bean定义或{@code getObjectType}的值判断，
     * 返回与给定对象类型（包括子类）匹配的bean实例。
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws Exception;

    /**
     * 查找加了某个指定注解的所有bean的名称。
     */
    String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType);

    /**
     * 查找加了某个指定注解的所有bean，返回带有相应bean实例的bean名称的映射。
     */
    Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws Exception;
}
