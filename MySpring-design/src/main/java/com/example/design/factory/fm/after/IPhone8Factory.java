package com.example.design.factory.fm.after;

import com.example.design.factory.sf.after.IPhone8;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public class IPhone8Factory implements Factory {
    @Override
    public IPhone8 create() {
        return new IPhone8();
    }
}
