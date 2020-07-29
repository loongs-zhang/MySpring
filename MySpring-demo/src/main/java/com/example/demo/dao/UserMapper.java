package com.example.demo.dao;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SuccessZhang
 * @date 2018/12/29
 */
@Repository
public interface UserMapper {

    /**
     * 保存用户信息
     *
     * @param user 待保存的用户信息
     */
    void save(User user);

    /**
     * 设置用户名
     *
     * @param originalName  原始用户名
     * @param targetName 修改后的用户名
     */
    void setName(@Param("originalName") String originalName,
                 @Param("targetName") String targetName);

    /**
     * 按照用户aid查询
     *
     * @param aid aid
     * @return 用户信息
     */
    User queryByAid(@Param("aid") Long aid);


    /**
     * 查询全部用户
     *
     * @return 用户信息列表
     */
    List<User> queryAll();

    /**
     * 删除全部用户
     *
     * @return 删除用户的数量
     */
    int deleteAll();

}