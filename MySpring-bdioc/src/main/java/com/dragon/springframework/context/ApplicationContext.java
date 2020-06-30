package com.dragon.springframework.context;

import com.dragon.springframework.beans.factory.ListableBeanFactory;
import com.dragon.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * 中央接口，为应用程序提供配置。
 *
 * @author SuccessZhang
 * @date 2020/06/10
 */
public interface ApplicationContext extends ListableBeanFactory, ApplicationEventPublisher {

    /**
     * 针对此上下文公开AutowireCapableBeanFactory功能。
     */
    AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException;

}
