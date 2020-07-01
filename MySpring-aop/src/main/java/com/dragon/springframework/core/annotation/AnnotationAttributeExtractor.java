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

package com.dragon.springframework.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * 负责从基础源提取属性值的属性提取器。
 *
 * @author SuccessZhang
 * @date 2020/06/11
 */
interface AnnotationAttributeExtractor {

    /**
     * 获取被提取属性的注解的类型。
     */
    Class<? extends Annotation> getAnnotationType();

    /**
     * 获取被注解标注的元素。
     */
    AnnotatedElement getAnnotatedElement();

    /**
     * 获取属性值。
     */
    Object getAttributeValue(String attribute);

}
