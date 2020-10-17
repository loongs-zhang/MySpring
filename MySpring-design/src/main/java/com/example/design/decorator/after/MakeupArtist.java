package com.example.design.decorator.after;

import com.example.design.decorator.before.Makeup;

/**
 * 化妆师。
 *
 * @author SuccessZhang
 * @date 2020/05/11
 */
public class MakeupArtist extends AbstractDecorator {

    private Makeup makeup;

    public MakeupArtist(AbstractWoman woman, Makeup makeup) {
        super(woman);
        this.makeup = makeup;
    }

    @Override
    public double getFaceValue() {
        return super.getFaceValue() + makeup.getAddedFaceValue();
    }
}
