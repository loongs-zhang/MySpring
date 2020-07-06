package com.dragon.springframework.context.context.support;

import com.dragon.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 带有内置XML支持的便捷应用程序上下文，是{@code ClassPathXmlApplicationContext}
 * 和{@code FileSystemXmlApplicationContext}的灵活替代方式，
 * 可以通过设置器进行配置，并最终激活了{@link #refresh()}调用。
 *
 * @author SuccessZhang
 * @date 2020/06/10
 */
public class GenericXmlApplicationContext extends GenericApplicationContext {

    private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);

    public GenericXmlApplicationContext() {
        try {
            reader.setBeanFactory(this.getBeanFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GenericXmlApplicationContext(String... resourceLocations) {
        try {
            reader.setBeanFactory(this.getBeanFactory());
            load(resourceLocations);
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(String... resourceLocations) {
        try {
            //定位配置文件
            this.reader.loadBeanDefinitions(resourceLocations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
