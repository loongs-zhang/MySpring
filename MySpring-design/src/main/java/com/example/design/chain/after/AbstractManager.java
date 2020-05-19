package com.example.design.chain.after;

import com.example.design.chain.before.ManagerLevel;
import com.example.design.chain.before.Request;

/**
 * @author SuccessZhang
 * @date 2020/05/19
 */
public abstract class AbstractManager {

    protected final ManagerLevel level;

    protected final AbstractManager successor;

    public AbstractManager(ManagerLevel level, AbstractManager successor) {
        this.level = level;
        this.successor = successor;
    }

    public abstract void handleRequest(Request request);
}
