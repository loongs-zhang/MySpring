package com.example.design.singleton.threadsafelazy;

import java.io.Serializable;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
@SuppressWarnings("unused")
public class SynchronizedLazySingleton implements Serializable {

    private static SynchronizedLazySingleton SINGLETON = null;

    private SynchronizedLazySingleton() {
        if (null != SINGLETON) {
            throw new RuntimeException("SINGLETON already exist!");
        }
    }

    private Object readResolve() {
        return SINGLETON;
    }

    public static synchronized SynchronizedLazySingleton getInstance() {
        if (null == SINGLETON) {
            SINGLETON = new SynchronizedLazySingleton();
        }
        return SINGLETON;
    }
}
