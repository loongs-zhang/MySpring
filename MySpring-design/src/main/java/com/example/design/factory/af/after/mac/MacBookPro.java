package com.example.design.factory.af.after.mac;

import lombok.Data;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
@Data
public class MacBookPro implements MacBook {
    @Override
    public void open() {
        System.out.println("MacBookPro开机");
    }
}
