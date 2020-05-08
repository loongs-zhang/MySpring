package com.example.design.factory.af.after;

import com.example.design.factory.af.after.iphone.IPhone;
import com.example.design.factory.af.after.mac.MacBook;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public interface AppleFactory {
    IPhone createIPhone();

    MacBook createMacBook();
}
