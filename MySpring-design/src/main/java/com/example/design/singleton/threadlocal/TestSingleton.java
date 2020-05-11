package com.example.design.singleton.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
public class TestSingleton {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        EXECUTOR.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " " + ThreadLocalSingleton.getInstance());
            System.out.println(Thread.currentThread().getName() + " " + ThreadLocalSingleton.getInstance());
        });
        EXECUTOR.submit(() -> {
            System.out.println(Thread.currentThread().getName() + " " + ThreadLocalSingleton.getInstance());
            System.out.println(Thread.currentThread().getName() + " " + ThreadLocalSingleton.getInstance());
        });
        EXECUTOR.shutdown();
    }
}
