package com.example.demo.transaction.propagation.mandatory;

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
public class MandatoryTest {

    @Autowired
    private MandatoryService mandatoryService;

    @Autowired
    private MandatorySuccessService mandatorySuccessService;

    public void test1() {
        mandatorySuccessService.success();
        mandatoryService.success();
        throw new RuntimeException("外面抛出异常");
    }

    public void test2() {
        mandatorySuccessService.success();
        mandatoryService.fail();
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class},
            transactionManager = "transactionManager",
            propagation = Propagation.MANDATORY)
    public void test3() {
        mandatorySuccessService.success();
        mandatoryService.success();
        throw new RuntimeException("外面抛出异常");
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class},
            transactionManager = "transactionManager",
            propagation = Propagation.MANDATORY)
    public void test4() {
        mandatorySuccessService.success();
        mandatoryService.fail();
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class},
            transactionManager = "transactionManager",
            propagation = Propagation.MANDATORY)
    public void test5() {
        mandatorySuccessService.success();
        try {
            mandatoryService.fail();
        }catch (Exception e){
            System.out.println("test5 exception");
        }
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
        MandatoryTest bean = context.getBean(MandatoryTest.class);
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
        System.out.println("---------------------------------------------------------");
        try {
            userMapper.deleteAll();
            bean.test5();
        } catch (Exception ignored) {
        }
        System.out.println("after:");
        printUsers(userMapper);
    }
}
