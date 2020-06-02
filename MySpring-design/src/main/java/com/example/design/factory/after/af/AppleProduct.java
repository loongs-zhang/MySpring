package com.example.design.factory.after.af;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public interface AppleProduct {
    default String getBrand() {
        return "Apple";
    }

    void open();
}
