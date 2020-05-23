package com.example.design.template.after;

import com.example.design.template.before.MySqlTransaction;
import com.example.design.template.before.Transaction;

/**
 * @author SuccessZhang
 * @date 2020/05/23
 */
@SuppressWarnings("unused")
public class UserDao2 extends AbstractDao<UserDao2> {

    public boolean saveUser(Transaction transaction) {
        System.out.println("保存用户");
        return true;
    }

    public boolean deleteUser(Transaction transaction) {
        throw new RuntimeException("资源不足");
    }

    public static void main(String[] args) {
        UserDao2 userDao = new UserDao2()
                .getProxy();
        System.out.println(userDao.saveUser(new MySqlTransaction()));
        System.out.println(userDao.deleteUser(new MySqlTransaction()));
    }
}
