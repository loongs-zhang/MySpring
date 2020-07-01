/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dragon.springframework.context.context.event;

import com.dragon.springframework.context.ApplicationContext;
import com.dragon.springframework.context.ApplicationEvent;
import com.dragon.springframework.context.ApplicationEventPublisher;
import com.dragon.springframework.context.ApplicationListener;

/**
 * 由可以管理多个{@link ApplicationListener}对象并向其发布事件的对象实现的接口。
 * {@link ApplicationEventPublisher}（通常是Spring {@link ApplicationContext}）
 * 可以将ApplicationEventMulticaster用作实际发布事件的委托。
 *
 * @author SuccessZhang
 * @date 2020/06/12
 */
public interface ApplicationEventMulticaster {

    /**
     * 添加一个监听器以通知所有事件。
     */
    void addApplicationListener(ApplicationListener<? extends ApplicationEvent> listener);

    /**
     * 添加一个监听器bean，以通知所有事件。
     */
    void addApplicationListenerBean(String listenerBeanName);

    /**
     * 从通知列表中删除一个监听器。
     */
    void removeApplicationListener(ApplicationListener<? extends ApplicationEvent> listener);

    /**
     * 从通知列表中删除一个监听器bean。
     */
    void removeApplicationListenerBean(String listenerBeanName);

    /**
     * 删除在此多播器上注册的所有监听器。
     * 在删除呼叫之后，多播器将不对事件通知执行任何操作，
     * 直到注册新的监听器为止。
     */
    void removeAllListeners();

    /**
     * 将给定的应用程序事件多播到适当的监听器。
     */
    void multicastEvent(ApplicationEvent event);

}
