package com.example.design.factory.af.after;

import com.example.design.factory.af.after.iphone.IPhone;
import com.example.design.factory.af.after.iphone.IPhone8;
import com.example.design.factory.af.after.mac.MacBook;
import com.example.design.factory.af.after.mac.MacBookAir;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public class AmericanAppleFactory implements AppleFactory {

    @Override
    public IPhone createIPhone() {
        return new IPhone8();
    }

    @Override
    public MacBook createMacBook() {
        return new MacBookAir();
    }
}
