package com.example.design.factory.fm.after;

import com.example.design.factory.sf.after.IPhone9;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public class IPhone9Factory implements Factory {
    @Override
    public IPhone9 create() {
        return new IPhone9();
    }
}
