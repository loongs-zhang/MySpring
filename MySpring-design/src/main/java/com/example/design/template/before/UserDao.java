package com.example.design.template.before;

/**
 * @author SuccessZhang
 * @date 2020/05/23
 */
@SuppressWarnings("unused")
public class UserDao {

    public boolean saveUser(Transaction transaction) {
        try {
            System.out.println("保存用户");
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    public boolean deleteUser(Transaction transaction) {
        try {
            throw new RuntimeException("资源不足");
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
            return false;
        }
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        System.out.println(userDao.saveUser(new MySqlTransaction()));
        System.out.println(userDao.deleteUser(new MySqlTransaction()));
    }

}
