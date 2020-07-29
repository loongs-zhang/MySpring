package com.example.demo.transaction.isolation.uncommitted;

import com.example.demo.dao.UserMapper;
import com.example.demo.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SuccessZhang
 * @date 2020/07/28
 */
@Component
public class UncommittedReadTest {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2);

    @Autowired
    private UserMapper userMapper;

    private static long aid;

    @Autowired
    private SqlSessionFactoryBean sqlSessionFactory;

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class},
            transactionManager = "transactionManager",
            isolation = Isolation.READ_UNCOMMITTED)
    public void set() {
        userMapper.setName("张三", "李四");
        System.out.println("set");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //手动回滚事务
        TransactionAspectSupport.currentTransactionStatus()
                .setRollbackOnly();
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class},
            transactionManager = "transactionManager",
            isolation = Isolation.READ_UNCOMMITTED)
    public void get() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            //通过mybatis原生的方式获取Mapper
            SqlSessionFactory factory = sqlSessionFactory.getObject();
            if (null == factory) {
                return;
            }
            SqlSession sqlSession = factory.openSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.queryByAid(aid);
            System.out.println("这里读到的是脏数据:" + user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:applicationContext.xml");
        UncommittedReadTest bean = context.getBean(UncommittedReadTest.class);
        UserMapper userMapper = context.getBean(UserMapper.class);
        User user = new User();
        user.setName("张三");
        userMapper.save(user);
        aid = user.getAid();
        System.out.println(userMapper.queryAll());
        EXECUTOR.submit(bean::set);
        EXECUTOR.submit(bean::get);
        EXECUTOR.shutdown();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(userMapper.queryAll());
        userMapper.deleteAll();
    }

}
