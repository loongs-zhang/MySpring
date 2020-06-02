package com.example.design.factory.after.af;

import com.example.design.factory.after.af.iphone.IPhone9;
import com.example.design.factory.after.af.mac.MacBookPro;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public class ChineseAppleFactory implements AppleFactory {

    @Override
    public IPhone9 createIPhone() {
        return new IPhone9();
    }

    @Override
    public MacBookPro createMacBook() {
        return new MacBookPro();
    }
}
