package com.example.demo.transaction;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * @author SuccessZhang
 */
@Component("myTransactionManager")
public class MyTransactionManager implements PlatformTransactionManager {

    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        return new DefaultTransactionStatus(null, true, true, definition.isReadOnly(), true, null);
    }

    @Override
    public void commit(TransactionStatus transactionStatus) throws TransactionException {
        System.out.println("commit");
    }

    @Override
    public void rollback(TransactionStatus transactionStatus) throws TransactionException {
        System.out.println("rollback");
    }
}
