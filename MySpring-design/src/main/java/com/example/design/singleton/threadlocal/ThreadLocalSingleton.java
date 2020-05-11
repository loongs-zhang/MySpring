package com.example.design.singleton.threadlocal;

import java.io.Serializable;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
@SuppressWarnings("unused")
public class ThreadLocalSingleton implements Serializable {
    private static final ThreadLocal<ThreadLocalSingleton> THREAD_LOCAL = new ThreadLocal<ThreadLocalSingleton>() {
        @Override
        protected ThreadLocalSingleton initialValue() {
            return new ThreadLocalSingleton();
        }
    };

    private ThreadLocalSingleton() {
    }

    private Object readResolve() {
        return THREAD_LOCAL.get();
    }

    public static ThreadLocalSingleton getInstance() {
        return THREAD_LOCAL.get();
    }
}
