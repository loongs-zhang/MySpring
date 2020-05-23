package com.example.design.template.before;

/**
 * @author SuccessZhang
 * @date 2020/05/23
 */
public interface Transaction {

    void commit();

    void rollback();
}
