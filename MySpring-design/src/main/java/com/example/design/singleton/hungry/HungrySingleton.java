package com.example.design.singleton.hungry;

import java.io.Serializable;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
@SuppressWarnings("unused")
public class HungrySingleton implements Serializable {

    private static final HungrySingleton SINGLETON = new HungrySingleton();

    private HungrySingleton() {
        if (null != SINGLETON) {
            throw new RuntimeException("SINGLETON already exist!");
        }
    }

    private Object readResolve() {
        return SINGLETON;
    }

    public static HungrySingleton getInstance() {
        return SINGLETON;
    }
}
