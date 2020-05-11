package com.example.design.singleton.innerstatic;

import java.io.Serializable;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
@SuppressWarnings("unused")
public class InnerStaticLazySingleton implements Serializable {

    private static class Holder {
        private static final InnerStaticLazySingleton SINGLETON = new InnerStaticLazySingleton();
    }

    private InnerStaticLazySingleton() {
        if (null != Holder.SINGLETON) {
            throw new RuntimeException("SINGLETON already exist!");
        }
    }

    private Object readResolve() {
        return Holder.SINGLETON;
    }

    public static InnerStaticLazySingleton getInstance() {
        return Holder.SINGLETON;
    }
}
