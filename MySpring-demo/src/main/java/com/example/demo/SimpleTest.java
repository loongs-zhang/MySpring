package com.example.demo;

import com.example.demo.controller.TestController;
import com.example.demo.service.TestService;
import com.example.demo.pojo.Type;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author SuccessZhang
 */
public class SimpleTest {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        System.out.println(context);
        TestController controller = (TestController) context.getBean("testController");
        System.out.println(controller);
        TestService testService = (TestService) context.getBean("testService");
        System.out.println(testService);
        System.out.println(testService.setType("1", new Type("vip")));
    }
}
