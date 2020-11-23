package com.example.design.composite.after.safe;

/**
 * @author SuccessZhang
 * @date 2020/05/27
 */
public abstract class AbstractNode2 {

    /**
     * 节点的层级
     */
    protected final int level;

    public AbstractNode2(int level) {
        this.level = level;
    }

    /**
     * 展示层级关系
     */
    public abstract void showLevel();
}
