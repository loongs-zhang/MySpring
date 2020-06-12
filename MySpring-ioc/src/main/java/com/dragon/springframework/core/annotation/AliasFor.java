/*
 * Copyright 2002-2015 the original author or authors.
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

package com.dragon.springframework.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 一个用于为注解属性声明别名的注解。
 *
 * @author SuccessZhang
 * @date 2020/06/11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AliasFor {

    /**
     * attribute属性的别名。
     */
    @AliasFor("attribute")
    String value() default "";

    /**
     * value属性的别名。
     */
    @AliasFor("value")
    String attribute() default "";

    /**
     * 别名的注解类型，默认为{@link Annotation}，
     * 表示别名属性是在与原属性相同的注解中声明的。
     */
    Class<? extends Annotation> annotation() default Annotation.class;

}
