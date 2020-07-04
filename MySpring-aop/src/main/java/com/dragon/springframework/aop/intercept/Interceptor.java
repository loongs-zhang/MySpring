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

package com.dragon.springframework.aop.intercept;

import com.dragon.springframework.aop.Advice;

/**
 * 此接口表示通用拦截器，通用拦截器可以拦截
 * 基本程序中发生的运行时事件，这些事件通过连接点实现。
 * 运行时连接点可以是调用，字段访问，异常等等。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public interface Interceptor extends Advice {

}