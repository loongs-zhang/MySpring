package com.example.design.decorator.before;

/**
 * 画眼影增加颜值。
 *
 * @author SuccessZhang
 * @date 2020/05/11
 */
public class EyeShadow implements Makeup {
    @Override
    public double getAddedFaceValue() {
        return 0.4;
    }
}
