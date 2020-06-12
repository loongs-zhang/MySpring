/*
 * Copyright 2002-2007 the original author or authors.
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

package com.dragon.springframework.beans.factory.support;

import com.dragon.springframework.beans.config.BeanDefinition;

/**
 * 用于为bean定义生成bean名称的策略接口。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
public interface BeanNameGenerator {

    /**
     * 为给定的bean定义生成一个bean名称。
     */
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);

}
