package com.example.design.principle.dip.follow;

/**
 * @author SuccessZhang
 * @date 2020/05/05
 */
@SuppressWarnings("unused")
public class Translator {

    public void sayHello(Language language) {
        language.hello();
    }
}
