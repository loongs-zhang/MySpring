package com.dragon.springframework.beans.factory.xml;

import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.beans.factory.support.BeanDefinitionRegistry;

/**
 * 专门用于{@link XmlBeanDefinitionReader}。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
public class XmlReaderContext {

    private final XmlBeanDefinitionReader reader;

    public XmlReaderContext(XmlBeanDefinitionReader reader) {
        this.reader = reader;
    }

    /**
     * 返回使用中的XML bean定义阅读器。
     */
    public final XmlBeanDefinitionReader getReader() {
        return this.reader;
    }

    /**
     * 返回要使用的bean定义注册表。
     */
    public final BeanDefinitionRegistry getRegistry() {
        return this.reader.getRegistry();
    }

    /**
     * 返回使用的Bean类加载器。
     */
    public final ClassLoader getBeanClassLoader() {
        return this.reader.getBeanClassLoader();
    }

    /**
     * 调用给定bean定义的bean名称生成器。
     */
    public String generateBeanName(BeanDefinition beanDefinition) {
        return this.reader.getBeanNameGenerator().generateBeanName(beanDefinition, getRegistry());
    }

    /**
     * 调用给定bean定义的bean名称生成器，并在生成的名称下注册bean定义。
     */
    public String registerWithGeneratedName(BeanDefinition beanDefinition) throws Exception {
        String generatedName = generateBeanName(beanDefinition);
        getRegistry().registerBeanDefinition(generatedName, beanDefinition);
        return generatedName;
    }
}
