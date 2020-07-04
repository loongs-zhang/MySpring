package com.dragon.springframework.context.context.annotation;

import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.beans.factory.xml.BeanDefinitionParser;
import com.dragon.springframework.beans.factory.xml.ParserContext;
import com.dragon.springframework.beans.factory.xml.XmlReaderContext;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    private static final String INTERNAL_PREFIX = "com.dragon.springframework.";

    private static final String[] INTERNAL = new String[]{
            INTERNAL_PREFIX + "core",
            INTERNAL_PREFIX + "beans",
            INTERNAL_PREFIX + "context",
            INTERNAL_PREFIX + "aop",
            INTERNAL_PREFIX + "tx",
            INTERNAL_PREFIX + "web",
            INTERNAL_PREFIX + "webmvc"
    };

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        String basePackage = element.attribute(BASE_PACKAGE_ATTRIBUTE).getData().toString();
        XmlReaderContext readerContext = parserContext.getReaderContext();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(
                readerContext.getRegistry());
        try {
            scanner.setBeanFactory(readerContext.getBeanFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<String> basePackages = new ArrayList<>(Arrays.asList(INTERNAL));
        basePackages.addAll(Arrays.asList(basePackage.split(",")));
        Set<BeanDefinition> beanDefinitions = scanner.doScan(basePackages.toArray(new String[0]));
        return null;
    }
}
