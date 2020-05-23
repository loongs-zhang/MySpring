package com.example.design.template.before;

/**
 * @author SuccessZhang
 * @date 2020/05/23
 */
public class MySqlTransaction implements Transaction {
    @Override
    public void commit() {
        System.out.println("MySQL提交事务");
    }

    @Override
    public void rollback() {
        System.out.println("MySQL回滚事务");
    }
}
