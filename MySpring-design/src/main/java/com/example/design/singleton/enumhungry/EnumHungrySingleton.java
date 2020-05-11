package com.example.design.singleton.enumhungry;

import java.io.Serializable;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
@SuppressWarnings("unused")
public enum EnumHungrySingleton implements Serializable {
    //枚举单例
    SINGLETON;

    /**
     * 这个方法其实可有可无，外部可以直接通过
     * EnumHungrySingleton.SINGLETON来访问。
     */
    public static EnumHungrySingleton getInstance() {
        return EnumHungrySingleton.SINGLETON;
    }
}
