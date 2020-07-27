package com.example.demo;

import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.Map;

/**
 * @author SuccessZhang
 * @date 2020/05/07
 */
public class SimpleTest2 {
    public static void main(String[] args) throws Exception {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:applicationContext.xml");
        System.out.println(context);
        Map<String, UserService> map = context.getBeansOfType(UserService.class);
        for (String name : map.keySet()) {
            System.out.println(name + " " + map.get(name).saveUser(new User()));
        }
    }
}
