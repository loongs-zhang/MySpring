package com.dragon.springframework.test;

import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.context.ApplicationEvent;
import com.dragon.springframework.context.ApplicationListener;
import com.dragon.springframework.context.context.support.GenericXmlApplicationContext;
import com.dragon.springframework.context.stereotype.Component;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

/**
 * @author SuccessZhang
 * @date 2020/06/12
 */
@Component
public class JavaBean {
    public static void main(String[] args) throws Exception {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:applicationContext.xml");
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = context.getBeanDefinition(beanDefinitionName);
            System.out.println(beanDefinition);
        }
        context.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                System.out.println(sdf.format(new Timestamp(event.getTimestamp()).toLocalDateTime()) + " " + event.getSource());
            }
        });
        context.publishEvent(new ApplicationEvent("Hello World") {
        });
        context.shutdownMulticaster();
    }
}
