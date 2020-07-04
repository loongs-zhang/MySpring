package com.dragon.springframework.beans.factory.support;

import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.beans.config.ConfigurableListableBeanFactory;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于bean定义对象的成熟bean工厂，典型用法是在访问bean之前
 * 先注册所有bean定义（可能从xml文件中读取）。因此，
 * Bean定义查找在本地Bean定义表中是一种廉价的操作。
 * 可用作独立的bean工厂，或用作自定义bean工厂的超类。
 * 请注意，特定bean定义格式的阅读器通常是单独实现的。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable {

    /**
     * Bean定义对象的映射，以Bean名称为键。
     */
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) {
        this.beanDefinitionMap.remove(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    @Override
    public Iterator<String> getBeanNamesIterator() {
        return this.beanDefinitionMap.keySet().iterator();
    }

    @Override
    public void preInstantiateSingletons() throws Exception {
        for (BeanDefinition beanDefinition : this.beanDefinitionMap.values()) {
            if (beanDefinition.isLazyInit()) {
                continue;
            }
            this.getBean(beanDefinition.getFactoryBeanName(), beanDefinition.getInitArguments());
        }
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> result = new ArrayList<>();
        String[] beanNames = this.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            try {
                Object bean = super.getBean(beanName, type);
                if (bean == null) {
                    continue;
                }
                result.add(beanName);
            } catch (Exception ignored) {
            }
        }
        return result.toArray(new String[0]);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public int getBeanDefinitionCount() {
        return beanDefinitionMap.keySet().size();
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws Exception {
        String[] beanNames = this.getBeanDefinitionNames();
        Map<String, T> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            T bean = super.getBean(beanName, type, this.getBeanDefinition(beanName).getInitArguments());
            if (bean == null) {
                continue;
            }
            result.put(beanName, bean);
        }
        return result;
    }

    @Override
    public String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        List<String> result = new ArrayList<>();
        String[] beanNames = this.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            try {
                BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
                Class<?> beanClass = beanDefinition.getBeanClass();
                if (beanClass.isAnnotationPresent(annotationType)) {
                    result.add(beanName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result.toArray(new String[0]);
    }

    @Override
    public Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws Exception {
        String[] beanNames = this.getBeanNamesForAnnotation(annotationType);
        Map<String, Object> result = new LinkedHashMap<>(beanNames.length);
        for (String beanName : beanNames) {
            result.put(beanName, super.getBean(beanName));
        }
        return result;
    }
}