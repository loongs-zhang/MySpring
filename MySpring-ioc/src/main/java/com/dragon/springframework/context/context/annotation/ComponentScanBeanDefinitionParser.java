package com.dragon.springframework.context.context.annotation;

import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.beans.factory.xml.BeanDefinitionParser;
import com.dragon.springframework.beans.factory.xml.ParserContext;
import com.dragon.springframework.beans.factory.xml.XmlReaderContext;
import org.dom4j.Element;

import java.util.Set;

/**
 * <context：component-scan />标签的解析器。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
public class ComponentScanBeanDefinitionParser implements BeanDefinitionParser {

    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String basePackage = element.attribute(BASE_PACKAGE_ATTRIBUTE).getData().toString();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner();
        String[] basePackages = basePackage.split(",");
        Set<BeanDefinition> beanDefinitions = scanner.doScan(basePackages);
        registerComponents(parserContext.getReaderContext(), beanDefinitions);
        return null;
    }

    private void registerComponents(XmlReaderContext readerContext, Set<BeanDefinition> beanDefinitions) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            try {
                readerContext.registerWithGeneratedName(beanDefinition);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
