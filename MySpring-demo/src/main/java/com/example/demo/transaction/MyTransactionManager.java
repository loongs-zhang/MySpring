package com.example.demo.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * @author SuccessZhang
 */
@Component("myTransactionManager")
public class MyTransactionManager extends AbstractPlatformTransactionManager {

    @Override
    protected Object doGetTransaction() throws TransactionException {
        Object transaction = new Object();
        System.out.println("doGetTransaction:" + transaction);
        return transaction;
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException {
        System.out.println("doBegin:" + transaction);
    }

    @Override
    protected void doCommit(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
        Object transaction = defaultTransactionStatus.getTransaction();
        System.out.println("doCommit:" + transaction);
    }

    @Override
    protected void doRollback(DefaultTransactionStatus defaultTransactionStatus) throws TransactionException {
        Object transaction = defaultTransactionStatus.getTransaction();
        System.out.println("doRollback:" + transaction);
    }
}
