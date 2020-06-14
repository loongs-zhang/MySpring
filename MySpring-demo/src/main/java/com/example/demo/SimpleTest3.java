package com.example.demo;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * Spring IOC源码导读入口。
 *
 * @author SuccessZhang
 * @date 2020/06/14
 */
public class SimpleTest3 {
    public static void main(String[] args) throws Exception {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:applicationContext.xml");
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = context.getBeanDefinition(beanDefinitionName);
            System.out.println(beanDefinition);
        }
    }
}
