package com.example.demo.service.impl;

import com.example.demo.pojo.Type;
import com.example.demo.pojo.User;
import com.example.demo.service.TestService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author SuccessZhang
 */
@SuppressWarnings("unused")
@Lazy
@Service("testService")
@Transactional(rollbackFor = Exception.class,
        transactionManager = "myTransactionManager")
public class TestServiceImpl implements TestService {

    @Override
    public User queryById(String id) {
        System.out.println("成功查询到了用户");
        User user = new User();
        user.setId(id);
        return user;
    }

    @Override
    public int setType(String id, Type type) {
        System.out.println("将id为" + id + "的用户设置为" + type + "类型成功");
        return 1;
    }
}
