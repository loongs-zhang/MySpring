package com.example.design.factory.af.after;

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
