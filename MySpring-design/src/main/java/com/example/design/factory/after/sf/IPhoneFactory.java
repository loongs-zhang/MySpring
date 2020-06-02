package com.example.design.factory.after.sf;

/**
 * @author SuccessZhang
 * @date 2020/05/08
 */
public class IPhoneFactory {
    @SuppressWarnings("unchecked")
    public static <T extends IPhone> T create(Class<T> clazz) {
        if (clazz.equals(IPhone8.class)) {
            return (T) new IPhone8();
        } else if (clazz.equals(IPhone9.class)) {
            return (T) new IPhone9();
        }
        return null;
    }
}
