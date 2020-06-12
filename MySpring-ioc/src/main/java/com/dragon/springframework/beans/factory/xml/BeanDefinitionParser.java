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

package com.dragon.springframework.beans.factory.xml;

import com.dragon.springframework.beans.config.BeanDefinition;
import org.dom4j.Element;

/**
 * {@link DefaultBeanDefinitionDocumentReader}用于处理顶级标签<beans/>下的接口。
 * 实现将标签中的元数据转换为所需的{@link BeanDefinition}Bean定义。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
public interface BeanDefinitionParser {

    /**
     * 解析指定的元素，并使用{@link ParserContext＃getRegistry}
     * 注册所生成的Bean定义信息。
     */
    BeanDefinition parse(Element element, ParserContext parserContext);

}
