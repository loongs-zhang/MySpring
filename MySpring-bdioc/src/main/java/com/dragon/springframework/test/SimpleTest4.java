package com.dragon.springframework.test;

import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.context.context.support.GenericXmlApplicationContext;
import com.dragon.springframework.context.stereotype.Component;

/**
 * @author SuccessZhang
 * @date 2020/06/12
 */
@Component
public class SimpleTest4 {
    public static void main(String[] args) throws Exception {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:applicationContext.xml");
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = context.getBeanDefinition(beanDefinitionName);
            System.out.println(beanDefinition);
        }
    }
}
