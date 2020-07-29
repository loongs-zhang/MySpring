package com.example.demo.transaction.isolation.repeatable;

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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SuccessZhang
 * @date 2020/07/28
 */
@Component
public class RepeatableReadTest {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SqlSessionFactoryBean sqlSessionFactory;

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class},
            transactionManager = "transactionManager",
            isolation = Isolation.REPEATABLE_READ)
    public void save() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user = new User();
        user.setName("李四");
        userMapper.save(user);
        System.out.println(user.getAid() + " saved");
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class},
            transactionManager = "transactionManager",
            isolation = Isolation.REPEATABLE_READ)
    public void get() {
        try {
            //通过mybatis原生的方式获取Mapper
            SqlSessionFactory factory = sqlSessionFactory.getObject();
            if (null == factory) {
                return;
            }
            SqlSession sqlSession = factory.openSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            System.out.println(mapper.queryAll());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //清除缓存，使得每次都去数据库拿最新的数据
            sqlSession.clearCache();
            System.out.println("可以看到Spring在REPEATABLE_READ级别就解决了幻读问题:" + mapper.queryAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GenericXmlApplicationContext context = new GenericXmlApplicationContext("classpath:applicationContext.xml");
        RepeatableReadTest bean = context.getBean(RepeatableReadTest.class);
        UserMapper userMapper = context.getBean(UserMapper.class);
        User user = new User();
        user.setName("张三");
        userMapper.save(user);
        EXECUTOR.submit(bean::save);
        EXECUTOR.submit(bean::get);
        EXECUTOR.shutdown();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userMapper.deleteAll();
    }

}
