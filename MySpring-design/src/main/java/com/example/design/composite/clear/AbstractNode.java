package com.example.design.composite.clear;

import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/27
 */
@SuppressWarnings("unused")
public abstract class AbstractNode {

    protected final int level;

    public AbstractNode(int level) {
        this.level = level;
    }

    public List<AbstractNode> getLowerLevel() {
        throw new UnsupportedOperationException();
    }

    public boolean addNode(AbstractNode node) {
        throw new UnsupportedOperationException();
    }

    public boolean removeNode(AbstractNode node) {
        throw new UnsupportedOperationException();
    }

    public abstract void showLevel();
}
