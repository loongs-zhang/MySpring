package com.dragon.springframework.context;

import com.dragon.springframework.beans.config.ConfigurableListableBeanFactory;

/**
 * 除了在{@link ApplicationContext}界面中的应用程序上下文客户端方法之外，
 * 还提供了配置应用程序上下文的功能。
 *
 * @author SuccessZhang
 * @date 2020/06/12
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 这是一种启动方法，如果失败，则应销毁已创建的单例，
     * 以避免资源悬空。换句话说，在调用该方法之后，
     * 应该实例化所有单例或根本不实例化。
     */
    void refresh() throws Exception;

    /**
     * 返回此应用程序上下文的内部bean工厂。
     */
    ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

}
