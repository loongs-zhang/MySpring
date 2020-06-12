package com.dragon.springframework.beans.factory.support;

import com.dragon.springframework.beans.BeanWrapper;
import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.beans.config.BeanPostProcessor;
import com.dragon.springframework.beans.config.ConfigurableBeanFactory;
import com.dragon.springframework.beans.factory.BeanFactory;
import com.dragon.springframework.beans.factory.FactoryBean;
import com.dragon.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.dragon.springframework.core.StringUtils;

import java.security.AccessController;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供Bean创建（带有构造函数解析），属性填充，
 * 接线（包括自动装配）和初始化。处理运行时bean引用，
 * 解析托管集合，调用初始化方法等。支持自动装配构造函数、
 * 名称（属性）和类型（属性）。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
public abstract class AbstractAutowireCapableBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, AutowireCapableBeanFactory {

    private final Set<BeanPostProcessor> beanPostProcessors = new HashSet<>();

    /**
     * 未完成的FactoryBean实例的缓存: FactoryBean name --> BeanWrapper
     */
    private final Map<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>(16);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        return this.getBean(beanName, (Object[]) null);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws Exception {
        return this.getBean(beanName, null, args);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws Exception {
        return this.getBean(requiredType, (Object[]) null);
    }

    @Override
    public <T> T getBean(Class<T> requiredType, Object... args) throws Exception {
        return this.getBean(StringUtils.lowerFirstCase(requiredType.getSimpleName()), requiredType, args);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws Exception {
        return this.getBean(beanName, requiredType, (Object[]) null);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType, Object... args) throws Exception {
        //todo 目前需要做的仅仅是注册bean的定义信息
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return super.containsSingleton(name) ||
                factoryBeanInstanceCache.containsKey(name);
    }

    @Override
    public boolean isSingleton(String name) throws Exception {
        if (name == null) {
            return false;
        }
        Object singleton = super.getSingleton(name);
        if (singleton == null) {
            return this.getBeanDefinition(name).isSingleton();
        }
        if (singleton instanceof FactoryBean) {
            return name.startsWith(BeanFactory.FACTORY_BEAN_PREFIX) || ((FactoryBean) singleton).isSingleton();
        }
        return !name.startsWith(BeanFactory.FACTORY_BEAN_PREFIX);
    }

    @Override
    public boolean isPrototype(String name) throws Exception {
        if (name == null) {
            return false;
        }
        return !this.isSingleton(name);
    }

    @Override
    public Class<?> getType(String name) throws Exception {
        return super.getSingleton(name).getClass();
    }

    @Override
    public <T> T createBean(Class<T> beanClass) throws Exception {
        return null;
    }

    @Override
    public Object initializeBean(Object existingBean, String beanName) throws Exception {
        return null;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws Exception {
        for (BeanPostProcessor processor : this.beanPostProcessors) {
            try {
                existingBean = processor.postProcessBeforeInitialization(existingBean, beanName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return existingBean;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws Exception {
        for (BeanPostProcessor processor : this.beanPostProcessors) {
            try {
                existingBean = processor.postProcessAfterInitialization(existingBean, beanName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return existingBean;
    }

    @Override
    public void destroyBean(Object existingBean) {
        new DisposableBeanAdapter(existingBean, this.beanPostProcessors, AccessController.getContext())
                .destroy();
    }

    @Override
    public void destroySingletons() {
        super.destroySingletons();
    }

    @Override
    protected void removeSingleton(String beanName) {
        super.removeSingleton(beanName);
        this.factoryBeanInstanceCache.remove(beanName);
    }

    /**
     * 返回给定bean名称的bean定义。子类通常应实现缓存，
     * 因为每次需要bean定义元数据时，此类便会调用此方法。
     * 根据具体bean工厂实现的性质，此操作可能很昂贵，
     * 但是对于可列出的bean工厂，这通常只相当于本地哈希查找，
     * 因此，该操作是其中公共接口的一部分。
     * 在这种情况下，此模板方法和公共接口方法都可以使用相同的实现。
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws Exception;
}