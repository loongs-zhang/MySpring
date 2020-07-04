package com.dragon.springframework.context.context.support;

import com.dragon.springframework.aop.framework.autoproxy.AspectJAwareAdvisorAutoProxyCreator;
import com.dragon.springframework.beans.config.ConfigurableListableBeanFactory;
import com.dragon.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.dragon.springframework.context.ApplicationContext;
import com.dragon.springframework.context.ApplicationEvent;
import com.dragon.springframework.context.ApplicationListener;
import com.dragon.springframework.context.ConfigurableApplicationContext;
import com.dragon.springframework.context.context.event.ApplicationEventMulticaster;
import com.dragon.springframework.context.context.event.SimpleApplicationEventMulticaster;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

/**
 * {@link ApplicationContext}接口的抽象实现，不强制用于配置的存储类型；
 * 简单地使用模板方法设计模式实现公共上下文功能，需要具体的子类来实现抽象方法。
 *
 * @author SuccessZhang
 * @date 2020/06/10
 */
public abstract class AbstractApplicationContext implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws Exception {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        //为BeanFactory配置容器特性
        prepareBeanFactory(beanFactory);
        try {
            //初始化容器事件传播器.
            initApplicationEventMulticaster();
            //为事件传播器注册事件监听器.
            registerListeners();
            //把非延迟加载的类提前初始化
            finishBeanFactoryInitialization(beanFactory);
        } catch (Exception e) {
            //初始化失败，销毁所有已创建的Bean
            destroyBeans();
        }
    }

    private void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        beanFactory.addBeanPostProcessor(new AspectJAwareAdvisorAutoProxyCreator(this));
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, this.applicationEventMulticaster);
    }

    @SuppressWarnings("unchecked")
    private void registerListeners() throws Exception {
        Collection<ApplicationListener> collection = getBeanFactory().getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : collection) {
            try {
                this.applicationEventMulticaster.addApplicationListener(listener);
            } catch (Exception ignored) {
            }
        }
    }

    private void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) throws Exception {
        beanFactory.preInstantiateSingletons();
    }

    private void destroyBeans() {
        getBeanFactory().destroySingletons();
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        if (event == null) {
            return;
        }
        this.applicationEventMulticaster.multicastEvent(event);
    }

    public final void addApplicationListener(ApplicationListener<? extends ApplicationEvent> listener) {
        this.applicationEventMulticaster.addApplicationListener(listener);
    }

    @Override
    public final AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException {
        return getBeanFactory();
    }

    @Override
    public final boolean containsBeanDefinition(String beanName) {
        return getBeanFactory().containsBeanDefinition(beanName);
    }

    @Override
    public final String[] getBeanNamesForType(Class<?> type) {
        return getBeanFactory().getBeanNamesForType(type);
    }

    @Override
    public final <T> Map<String, T> getBeansOfType(Class<T> type) throws Exception {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public final String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
        return getBeanFactory().getBeanNamesForAnnotation(annotationType);
    }

    @Override
    public final Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) throws Exception {
        return getBeanFactory().getBeansWithAnnotation(annotationType);
    }

    @Override
    public final Object getBean(String beanName) throws Exception {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public final Object getBean(String beanName, Object... args) throws Exception {
        return getBeanFactory().getBean(beanName, args);
    }

    @Override
    public final <T> T getBean(Class<T> requiredType) throws Exception {
        return getBeanFactory().getBean(requiredType);
    }

    @Override
    public final <T> T getBean(Class<T> requiredType, Object... args) throws Exception {
        return getBeanFactory().getBean(requiredType, args);
    }

    @Override
    public final <T> T getBean(String beanName, Class<T> requiredType) throws Exception {
        return getBeanFactory().getBean(beanName, requiredType);
    }

    @Override
    public final <T> T getBean(String beanName, Class<T> requiredType, Object... args) throws Exception {
        return getBeanFactory().getBean(beanName, requiredType, args);
    }

    @Override
    public final boolean containsBean(String name) {
        return getBeanFactory().containsBean(name);
    }

    @Override
    public final boolean isSingleton(String name) throws Exception {
        return getBeanFactory().isSingleton(name);
    }

    @Override
    public final boolean isPrototype(String name) throws Exception {
        return getBeanFactory().isPrototype(name);
    }

    @Override
    public final Class<?> getType(String name) throws Exception {
        return getBeanFactory().getType(name);
    }
}
