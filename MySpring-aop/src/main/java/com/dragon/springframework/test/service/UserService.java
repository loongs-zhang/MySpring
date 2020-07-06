package com.dragon.springframework.test.service;

import com.dragon.springframework.context.stereotype.Service;
import com.dragon.springframework.test.pojo.Type;
import com.dragon.springframework.test.pojo.User;

/**
 * 用于测试Cglib动态代理。
 *
 * @author SuccessZhang
 * @date 2020/07/05
 */
@Service
public class UserService {

    public User queryById(String id) {
        System.out.println("成功查询到了用户");
        User user = new User();
        user.setId(id);
        return user;
    }

    public int setType(String id, Type type) {
        System.out.println("将id为" + id + "的用户设置为" + type + "类型成功");
        return 1;
    }
}
