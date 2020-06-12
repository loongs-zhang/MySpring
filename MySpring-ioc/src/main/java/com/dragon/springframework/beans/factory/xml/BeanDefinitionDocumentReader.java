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

package com.dragon.springframework.beans.factory.xml;

import org.dom4j.Document;

/**
 * 用于解析包含Spring bean定义的XML文档。
 * 实际用{@link XmlBeanDefinitionReader}解析DOM文档。
 * 每个文档都要实例化，在执行registerBeanDefinitions方法时，
 * 可以实现在实例变量中保存状态，例如为文档中所有bean定义定义的全局设置。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
public interface BeanDefinitionDocumentReader {

    /**
     * 从给定的DOM文档中读取bean定义，并在给定的阅读器上下文中向注册表注册它们。
     */
    void registerBeanDefinitions(Document doc, XmlReaderContext readerContext)
            throws Exception;

}
