package com.example.design.factory.after.af;

import com.example.design.factory.after.af.iphone.IPhone;
import com.example.design.factory.after.af.iphone.IPhone8;
import com.example.design.factory.after.af.mac.MacBook;
import com.example.design.factory.after.af.mac.MacBookAir;

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
