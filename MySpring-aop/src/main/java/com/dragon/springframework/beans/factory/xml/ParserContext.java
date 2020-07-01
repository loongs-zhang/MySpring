package com.dragon.springframework.beans.factory.xml;

import lombok.Getter;

/**
 * 在bean定义解析过程中传递的上下文，封装了所有相关的配置和状态。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
public class ParserContext {

    @Getter
    private final XmlReaderContext readerContext;

    public ParserContext(XmlReaderContext readerContext) {
        this.readerContext = readerContext;
    }
}
