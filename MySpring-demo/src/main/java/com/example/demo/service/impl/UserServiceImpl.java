package com.example.demo.service.impl;

import com.example.demo.annotation.TransactionalService;
import com.example.demo.pojo.User;
import com.example.demo.service.UserService;

/**
 * @author SuccessZhang
 * @date 2020/05/07
 */
@TransactionalService(value = "userService-2020/05/07",
        transactionManager = "myTransactionManager")
public class UserServiceImpl implements UserService {
    @Override
    public boolean saveUser(User user) {
        System.out.println("添加用户成功");
        return true;
    }
}
