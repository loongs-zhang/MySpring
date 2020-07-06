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

/**
 * 该接口表示通用的运行时连接点，运行时连接点是在静态连接点
 * （即程序中的某个位置）上发生的事件，例如调用是方法上的
 * 运行时连接点（静态连接点）。在拦截框架的上下文中，运行时连接点
 * 是对可访问对象（方法、构造函数、字段，即连接点的静态部分）的访问的验证。
 * 它被传递给安装在静态连接点上的拦截器。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public interface JoinPoint {

    /**
     * 继续执行链中的下一个拦截器。
     */
    Object proceed() throws Throwable;

    /**
     * 返回保存当前联接点的静态部分的代理对象。
     */
    Object getThis();

    /**
     * 返回通知目标对象。
     */
    Object getTarget();

    /**
     * 返回此连接点的参数。
     */
    Object[] getArgs();

}
