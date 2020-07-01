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

package com.dragon.springframework.beans.factory;

/**
 * 允许bean知道该bean的ClassLoader的回调{@link ClassLoader class loader};
 * 也就是说，当前bean工厂使用指定的类加载器加载该bean。
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
public interface BeanClassLoaderAware extends Aware {

    /**
     * 向bean实例提供ClassLoader{@link ClassLoader class loader}的回调。
     * 在填充普通bean属性之后，并在bean真正初始化之前，进行回调。
     */
    void setBeanClassLoader(ClassLoader classLoader);

}
