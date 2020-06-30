package com.dragon.springframework.core;

import java.util.function.Supplier;

/**
 * 声明实用程序类，有助于验证参数。
 *
 * @author SuccessZhang
 * @date 2020/06/17
 */
public class Assert {

    /**
     * 声明一个布尔表达式，如果该表达式的值为false，
     * 抛出一个IllegalArgumentException。
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 声明一个布尔表达式，如果该表达式的值为false，
     * 抛出一个IllegalArgumentException。
     */
    public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }

    /**
     * 断言对象不是null。
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言对象不是null。
     */
    public static void notNull(Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new IllegalArgumentException(nullSafeGet(messageSupplier));
        }
    }

    private static String nullSafeGet(Supplier<String> messageSupplier) {
        return (messageSupplier != null ? messageSupplier.get() : null);
    }

}
