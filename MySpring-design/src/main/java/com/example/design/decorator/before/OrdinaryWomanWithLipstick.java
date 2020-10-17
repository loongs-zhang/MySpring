package com.example.design.decorator.before;

/**
 * 涂了口红的妹子。
 *
 * @author SuccessZhang
 * @date 2020/05/11
 */
public class OrdinaryWomanWithLipstick extends OrdinaryWoman {
    @Override
    public double getFaceValue() {
        return super.getFaceValue() + new Lipstick().getAddedFaceValue();
    }
}
