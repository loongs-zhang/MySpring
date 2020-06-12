package com.dragon.springframework.beans.factory.support;

import com.dragon.springframework.beans.config.SingletonBeanRegistry;
import com.dragon.springframework.beans.factory.DisposableBean;

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
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 缓存单例对象: bean name --> bean instance
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

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
}
