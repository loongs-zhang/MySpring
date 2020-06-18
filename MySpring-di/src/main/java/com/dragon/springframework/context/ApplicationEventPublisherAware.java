/*
 * Copyright 2002-2011 the original author or authors.
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

package com.dragon.springframework.context;

import com.dragon.springframework.beans.factory.Aware;

/**
 * 希望获取在其运行的ApplicationEventPublisher（通常是ApplicationContext）
 * 的对象实现的接口。
 *
 * @author SuccessZhang
 * @date 2020/06/12
 */
public interface ApplicationEventPublisherAware extends Aware {

    /**
     * 设置运行该对象的ApplicationEventPublisher。
     */
    void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher);

}
