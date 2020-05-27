package com.example.design.composite.safe;

/**
 * @author SuccessZhang
 * @date 2020/05/27
 */
@SuppressWarnings("unused")
public abstract class AbstractNode2 {

    protected final int level;

    public AbstractNode2(int level) {
        this.level = level;
    }

    public abstract void showLevel();
}
