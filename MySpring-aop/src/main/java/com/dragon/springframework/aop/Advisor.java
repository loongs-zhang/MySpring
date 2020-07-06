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

package com.dragon.springframework.aop;

/**
 * 具有AOP通知（在连接点采取的操作）和确定通知适用性过滤器
 * 的基本接口，Spring用户不使用此界面，而是允许通用性来支持
 * 不同类型的通知。Spring AOP基于环绕通知通过方法拦截传递，
 * Advisor界面允许支持不同类型的通知，例如之前和之后通知，
 * 这些通知无需使用拦截实现。
 *
 * @author SuccessZhang
 * @date 2020/07/03
 */
public interface Advisor {

    /**
     * 返回此切面的通知部分，通知可以是拦截器，
     * 事前通知，抛出通知等。
     */
    Advice getAdvice();

}
