package com.example.demo.transaction.propagation.notsupported;

import com.example.demo.dao.UserMapper;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/07/27
 */
@Component
public class NotSupportedTest {

    @Autowired
    private NotSupportedService notSupportedService;

    @Autowired
    private NotSupportedSuccessService notSupportedSuccessService;

    public void test1() {
        notSupportedSuccessService.success();
        notSupportedService.success();
        throw new RuntimeException("外面抛出异常");
    }

    public void test2() {
        notSupportedSuccessService.success();
        notSupportedService.fail();
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class},
            transactionManager = "transactionManager",
            propagation = Propagation.REQUIRED)
    public void test3() {
        notSupportedSuccessService.success();
        notSupportedService.success();
        throw new RuntimeException("外面抛出异常");
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class},
            transactionManager = "transactionManager",
            propagation = Propagation.REQUIRED)
    public void test4() {
        notSupportedSuccessService.success();
        notSupportedService.fail();
    }

    private static void printUsers(UserMapper userMapper) {
        List<User> users = userMapper.queryAll();
        for (User user : users) {
            System.out.println(user.getAid() + " " + user.getName());
        }
    }

    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:applicationContext.xml");
        UserMapper userMapper = context.getBean(UserMapper.class);
        NotSupportedTest bean = context.getBean(NotSupportedTest.class);
        try {
            userMapper.deleteAll();
            bean.test1();
        } catch (Exception ignored) {
        }
        System.out.println("after:");
        printUsers(userMapper);
        System.out.println("---------------------------------------------------------");
        try {
            userMapper.deleteAll();
            bean.test2();
        } catch (Exception ignored) {
        }
        System.out.println("after:");
        printUsers(userMapper);
        System.out.println("---------------------------------------------------------");
        try {
            userMapper.deleteAll();
            bean.test3();
        } catch (Exception ignored) {
        }
        System.out.println("after:");
        printUsers(userMapper);
        System.out.println("---------------------------------------------------------");
        try {
            userMapper.deleteAll();
            bean.test4();
        } catch (Exception ignored) {
        }
        System.out.println("after:");
        printUsers(userMapper);
    }
}
