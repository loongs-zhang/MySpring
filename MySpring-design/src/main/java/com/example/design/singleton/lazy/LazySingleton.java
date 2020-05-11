package com.example.design.singleton.lazy;

import java.io.Serializable;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
@SuppressWarnings("unused")
public class LazySingleton implements Serializable {

    private static LazySingleton SINGLETON = null;

    private LazySingleton() {
        if (null != SINGLETON) {
            throw new RuntimeException("SINGLETON already exist!");
        }
    }

    private Object readResolve() {
        return SINGLETON;
    }

    public static LazySingleton getInstance() {
        if (null == SINGLETON) {
            SINGLETON = new LazySingleton();
        }
        return SINGLETON;
    }
}
