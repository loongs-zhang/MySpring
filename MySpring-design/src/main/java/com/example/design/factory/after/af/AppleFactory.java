package com.example.design.factory.after.af;

import com.example.design.factory.after.af.iphone.IPhone;
import com.example.design.factory.after.af.mac.MacBook;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public interface AppleFactory {
    IPhone createIPhone();

    MacBook createMacBook();
}
