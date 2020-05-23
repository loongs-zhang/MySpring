package com.example.design.template.jdbc;

import com.example.design.template.jdbc.pojo.User;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/23
 */
public class UserDao extends DaoTemplate<User> {

    public UserDao(String url, String user, String password) {
        super(url, user, password);
    }

    @Override
    protected User mapping(ResultSet rs) throws Exception {
        return User.builder()
                .aid(rs.getLong("aid"))
                .avatar(rs.getString("avatar"))
                .name(rs.getString("name"))
                .gender(rs.getString("gender"))
                .type(rs.getString("type"))
                .lastLoginDate(rs.getDate("last_login_date"))
                .createTime(rs.getDate("create_time"))
                .updateTime(rs.getDate("update_time"))
                .dr(rs.getBoolean("dr"))
                .build();
    }

    public static void main(String[] args) throws Exception {
        UserDao userDao = new UserDao("jdbc:mysql://127.0.0.1:3306/shop_anywhere?useSSL=false", "root", "root");
        List<User> users = userDao.executeQuery("select * from `user` where aid =\"1\"");
        userDao.closeConnection();
        System.out.println(users);
    }
}
