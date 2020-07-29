package com.example.demo.transaction.propagation.nested;

import com.example.demo.dao.UserMapper;
import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author SuccessZhang
 * @date 2020/07/27
 */
@Service
public class NestedService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class},
            transactionManager = "transactionManager",
            propagation = Propagation.NESTED)
    public void fail() {
        User user = new User();
        user.setName("李四");
        userMapper.save(user);
        System.out.println("fail");
        throw new RuntimeException("故意抛出异常");
    }

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class},
            transactionManager = "transactionManager",
            propagation = Propagation.NESTED)
    public void success() {
        User user = new User();
        user.setName("李四");
        userMapper.save(user);
    }
}
