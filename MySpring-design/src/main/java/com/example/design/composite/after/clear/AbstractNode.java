package com.example.design.composite.after.clear;

import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/27
 */
public abstract class AbstractNode {

    /**
     * 节点的层级
     */
    protected final int level;

    public AbstractNode(int level) {
        this.level = level;
    }

    /**
     * 获取低一层级
     */
    public List<AbstractNode> getLowerLevel() {
        throw new UnsupportedOperationException();
    }

    /**
     * 添加节点
     */
    public boolean addNode(AbstractNode node) {
        throw new UnsupportedOperationException();
    }

    /**
     * 删除节点
     */
    public boolean removeNode(AbstractNode node) {
        throw new UnsupportedOperationException();
    }

    /**
     * 展示层级关系
     */
    public abstract void showLevel();
}
