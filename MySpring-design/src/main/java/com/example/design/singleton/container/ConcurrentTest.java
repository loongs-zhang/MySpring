package com.example.design.singleton.container;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SuccessZhang
 * @date 2020/05/18
 */
public class ConcurrentTest {

    private static ExecutorService executorService = Executors.newFixedThreadPool(6);

    public static void main(String[] args) {
        for (int i = 0; i < 600; i++) {
            executorService.submit(() -> {
                try {
                    if (ContainerSingleton.getInstance(ConcurrentTest.class) == null) {
                        System.out.println("null value");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
