package com.dragon.springframework.beans.factory.xml;

import com.dragon.springframework.context.context.annotation.ComponentScanBeanDefinitionParser;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;

/**
 * {@link BeanDefinitionDocumentReader}接口的默认实现，
 * 将解析XML文件中的所有bean定义元素，root元素可以不是<beans/>。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader {

    @Override
    @SuppressWarnings("unchecked")
    public void registerBeanDefinitions(Document doc, XmlReaderContext readerContext) throws Exception {
        //获取根节点<beans>
        Element root = doc.getRootElement();
        //获取子节点列表
        List<Element> elements = root.elements();
        BeanDefinitionParser parser = new ComponentScanBeanDefinitionParser();
        parser.parse(elements.get(0), new ParserContext(readerContext));
    }
}
