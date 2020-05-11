package com.example.design.decorator.after;

/**
 * @author SuccessZhang
 * @date 2020/05/11
 */
public class OrdinaryWomanWithLipstickAndEyeShadow extends OrdinaryWomanWithLipstick {
    @Override
    public double getFaceValue() {
        return super.getFaceValue() + new EyeShadow().getAddedFaceValue();
    }
}
