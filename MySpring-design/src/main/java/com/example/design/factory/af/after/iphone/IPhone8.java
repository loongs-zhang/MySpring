package com.example.design.factory.af.after.iphone;

import lombok.Data;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
@Data
public class IPhone8 implements IPhone {
    @Override
    public void open() {
        System.out.println("iPhone8开机");
    }
}
