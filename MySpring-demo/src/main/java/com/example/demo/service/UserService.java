package com.example.demo.service;

import com.example.demo.pojo.User;

/**
 * @author SuccessZhang
 * @date 2020/05/07
 */
public interface UserService {
    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 是否添加成功
     */
    boolean saveUser(User user);
}
