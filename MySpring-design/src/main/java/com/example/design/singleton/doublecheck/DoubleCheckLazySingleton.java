package com.example.design.singleton.doublecheck;

import java.io.Serializable;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
@SuppressWarnings("unused")
public class DoubleCheckLazySingleton implements Serializable {

    private static DoubleCheckLazySingleton SINGLETON = null;

    private DoubleCheckLazySingleton() {
        if (null != SINGLETON) {
            throw new RuntimeException("SINGLETON already exist!");
        }
    }

    private Object readResolve() {
        return SINGLETON;
    }

    public static DoubleCheckLazySingleton getInstance() {
        if (null == SINGLETON) {
            synchronized (DoubleCheckLazySingleton.class) {
                if (null == SINGLETON) {
                    SINGLETON = new DoubleCheckLazySingleton();
                }
            }
        }
        return SINGLETON;
    }
}
