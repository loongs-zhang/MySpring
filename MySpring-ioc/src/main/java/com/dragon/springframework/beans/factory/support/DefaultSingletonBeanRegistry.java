package com.dragon.springframework.beans.factory.support;

import com.dragon.springframework.beans.config.SingletonBeanRegistry;
import com.dragon.springframework.beans.factory.DisposableBean;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 共享bean实例的通用注册表，允许注册应该由bean名称获得的所有注册表
 * 调用者共享的单例实例，还支持{@link DisposableBean}的实例在注册表
 * 关闭时销毁。由于它们之间的依赖关系，可以强制执行适当的关闭命令。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
@Slf4j
@SuppressWarnings("unused")
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 缓存单例对象: bean name --> bean instance
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /**
     * DisposableBean实例: bean name --> disposable instance
     */
    private final Map<String, Object> disposableBeans = new LinkedHashMap<>();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return singletonObjects.containsKey(beanName);
    }

    @Override
    public String[] getSingletonNames() {
        return singletonObjects.keySet().toArray(new String[0]);
    }

    @Override
    public int getSingletonCount() {
        return singletonObjects.keySet().size();
    }

    /**
     * 将给定的bean添加到此注册表中的DisposableBean列表中。
     */
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        synchronized (this.disposableBeans) {
            this.disposableBeans.put(beanName, bean);
        }
    }

    /**
     * 销毁所有单例。
     */
    public void destroySingletons() {
        String[] disposableBeanNames;
        synchronized (this.disposableBeans) {
            disposableBeanNames = this.disposableBeans.keySet().toArray(new String[0]);
        }
        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            destroySingleton(disposableBeanNames[i]);
        }
        synchronized (this.singletonObjects) {
            this.singletonObjects.clear();
        }
    }

    /**
     * 销毁给定的bean。如果找到的是DisposableBean实例，
     * 则委托给destroyBean方法。
     */
    public void destroySingleton(String beanName) {
        // 删除给定名称的已注册单例
        removeSingleton(beanName);
        // 销毁相应的DisposableBean实例
        DisposableBean disposableBean;
        synchronized (this.disposableBeans) {
            disposableBean = (DisposableBean) this.disposableBeans.remove(beanName);
        }
        destroyBean(beanName, disposableBean);
    }

    /**
     * 从该工厂的单例缓存中删除具有给定名称的Bean，
     * 以便在创建失败时快速清除注册的单例。
     */
    protected void removeSingleton(String beanName) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.remove(beanName);
        }
    }

    /**
     * 销毁给定的bean，不应抛出任何异常。
     */
    private void destroyBean(String beanName, DisposableBean bean) {
        if (bean == null) {
            return;
        }
        try {
            bean.destroy();
        } catch (Throwable ex) {
            log.error("Destroy method on bean with name '" + beanName + "' threw an exception", ex);
        }
    }
}
