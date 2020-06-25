/*
 * Copyright 2002-2016 the original author or authors.
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

package com.dragon.springframework.beans.factory.config;

import com.dragon.springframework.beans.config.BeanPostProcessor;

/**
 * {@link BeanPostProcessor}的子接口，用于添加破坏前的回调。
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
public interface DestructionAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在销毁该BeanPostProcessor之前，将其应用于给定的bean实例。可以
     * 调用自定义销毁回调，此回调仅适用于工厂中的单例bean（包括内部bean）。
     */
    void postProcessBeforeDestruction(Object bean, String beanName) throws Exception;

    /**
     * 确定给定的b​​ean实例是否需要此后处理器销毁。
     */
    default boolean requiresDestruction(Object bean) {
        return true;
    }

}
