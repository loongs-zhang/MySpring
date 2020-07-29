package com.example.demo.transaction.propagation.requiresnew;

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
public class RequiresNewSuccessService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = {RuntimeException.class, Exception.class},
            transactionManager = "transactionManager",
            propagation = Propagation.REQUIRES_NEW)
    public void success() {
        User user = new User();
        user.setName("张三");
        userMapper.save(user);
        System.out.println("success");
    }
}
