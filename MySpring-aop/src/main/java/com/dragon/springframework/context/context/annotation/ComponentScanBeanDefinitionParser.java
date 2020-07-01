package com.dragon.springframework.context.context.annotation;

import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.beans.factory.xml.BeanDefinitionParser;
import com.dragon.springframework.beans.factory.xml.ParserContext;
import org.dom4j.Element;

import java.util.Set;

/**
 * <context：component-scan />标签的解析器。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
@SuppressWarnings("unused")
public class ComponentScanBeanDefinitionParser implements BeanDefinitionParser {

    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String basePackage = element.attribute(BASE_PACKAGE_ATTRIBUTE).getData().toString();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(
                parserContext.getReaderContext().getRegistry());
        String[] basePackages = basePackage.split(",");
        Set<BeanDefinition> beanDefinitions = scanner.doScan(basePackages);
        return null;
    }
}
