/*
 * Copyright 2002-2012 the original author or authors.
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

package com.dragon.springframework.beans.factory;

/**
 * 希望意识到自己拥有{@link BeanFactory}的bean所实现的接口。
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
public interface BeanFactoryAware extends Aware {

    /**
     * 将拥有的工厂提供给Bean实例的回调。
     * 在填充普通bean属性之后，并在bean真正初始化之前，进行回调。
     */
    void setBeanFactory(BeanFactory beanFactory) throws Exception;

}
