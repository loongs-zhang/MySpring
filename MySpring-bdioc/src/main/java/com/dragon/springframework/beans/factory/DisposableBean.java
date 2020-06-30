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

package com.dragon.springframework.beans.factory;

/**
 * 要在销毁时释放资源的Bean所实现的接口。
 * 如果BeanFactory处理已缓存的单例，则应该调用destroy方法。
 * 应用程序上下文应该在关闭时处理其所有单例。
 * 实现DisposableBean的另一种方法是指定自定义destroy-method，
 * 例如在XML的bean定义中。
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
public interface DisposableBean {

    /**
     * 由BeanFactory在销毁Bean单例上调用。
     */
    void destroy() throws Exception;

}
