package com.dragon.springframework.test;

import com.dragon.springframework.context.context.support.GenericXmlApplicationContext;
import com.dragon.springframework.context.stereotype.Component;

/**
 * @author SuccessZhang
 * @date 2020/06/12
 */
@Component
public class SimpleTest5 {
    public static void main(String[] args) throws Exception {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:applicationContext.xml");
        for (String beanName : context.getBeanDefinitionNames()) {
            Object bean = context.getBean(beanName);
            System.out.println(beanName + " " + bean);
        }
    }
}
