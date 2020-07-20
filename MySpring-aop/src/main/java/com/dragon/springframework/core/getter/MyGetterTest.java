package com.dragon.springframework.core.getter;

import com.dragon.springframework.lombok.Getter;

/**
 * @author SuccessZhang
 * @date 2020/07/20
 */
@Getter
public class MyGetterTest {

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public static void main(String[] args) {
        MyGetterTest test = new MyGetterTest();
        test.setValue("it works");
        System.out.println(test.getValue());
    }
}
