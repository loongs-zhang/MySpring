package com.dragon.springframework.context.context.event;

import com.dragon.springframework.beans.factory.BeanFactory;
import com.dragon.springframework.beans.factory.BeanFactoryAware;
import com.dragon.springframework.context.ApplicationEvent;
import com.dragon.springframework.context.ApplicationListener;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link ApplicationEventMulticaster}接口的简单实现。
 * 将所有事件多播到所有已注册的侦听器，从而留给侦听器
 * 忽略他们不感​​兴趣的事件。侦听器通常将对传入的事件对象
 * 执行相应的{@code instanceof}检查。
 *
 * @author SuccessZhang
 * @date 2020/06/12
 */
public class SimpleApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    private final ExecutorService executor = Executors.newFixedThreadPool(6);

    private BeanFactory beanFactory;

    private final Set<ApplicationListener<? extends ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        this.setBeanFactory(beanFactory);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void addApplicationListener(ApplicationListener<? extends ApplicationEvent> listener) {
        this.applicationListeners.add(listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<? extends ApplicationEvent> listener) {
        this.applicationListeners.remove(listener);
    }

    @Override
    public void addApplicationListenerBean(String listenerBeanName) {
        try {
            ApplicationListener<?> listener = beanFactory.getBean(listenerBeanName, ApplicationListener.class);
            if (listener != null) {
                this.addApplicationListener(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeApplicationListenerBean(String listenerBeanName) {
        try {
            ApplicationListener<?> listener = beanFactory.getBean(listenerBeanName, ApplicationListener.class);
            if (listener != null) {
                this.removeApplicationListener(listener);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAllListeners() {
        this.applicationListeners.clear();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener listener : this.applicationListeners) {
            this.executor.execute(() -> listener.onApplicationEvent(event));
        }
    }

    @Override
    public void shutdown() {
        this.executor.shutdown();
    }

}
