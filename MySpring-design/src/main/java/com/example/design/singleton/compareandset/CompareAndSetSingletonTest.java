package com.example.design.singleton.compareandset;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SuccessZhang
 * @date 2020/05/18
 */
public class CompareAndSetSingletonTest {

    private static ExecutorService executorService = Executors.newFixedThreadPool(12);

    public static void main(String[] args) {
        for (int i = 0; i < 600; i++) {
            executorService.submit((Runnable) CompareAndSetSingleton::getInstance);
        }
        executorService.shutdown();
    }
}
