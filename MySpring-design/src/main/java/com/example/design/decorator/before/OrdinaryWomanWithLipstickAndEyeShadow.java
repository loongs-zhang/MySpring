package com.example.design.decorator.before;

/**
 * 涂了口红并且画了眼影的妹子。
 *
 * @author SuccessZhang
 * @date 2020/05/11
 */
public class OrdinaryWomanWithLipstickAndEyeShadow extends OrdinaryWomanWithLipstick {
    @Override
    public double getFaceValue() {
        return super.getFaceValue() + new EyeShadow().getAddedFaceValue();
    }
}
