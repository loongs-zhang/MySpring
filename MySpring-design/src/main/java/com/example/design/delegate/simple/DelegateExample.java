package com.example.design.delegate.simple;

import java.util.Random;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public class DelegateExample {

    private static void doDelegate(Random random, int i) {
        switch (random.nextInt(4)) {
            case 0:
                System.out.println(i + "一二三四五");
                break;
            case 1:
                System.out.println(i + "上山打老虎");
                break;
            case 2:
                System.out.println(i + "老虎没打到");
                break;
            case 3:
                System.out.println(i + "打到小老鼠");
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            doDelegate(random, i);
        }
    }
}
