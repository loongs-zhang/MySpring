package com.example.design.decorator.before;

/**
 * 涂口红增加颜值。
 *
 * @author SuccessZhang
 * @date 2020/05/11
 */
public class Lipstick implements Makeup {
    @Override
    public double getAddedFaceValue() {
        return 0.3;
    }
}
