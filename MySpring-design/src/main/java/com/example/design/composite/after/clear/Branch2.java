package com.example.design.composite.after.clear;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/27
 */
public class Branch2 extends AbstractNode {

    private List<AbstractNode> nodes = new ArrayList<>();

    public Branch2(int level) {
        super(level);
    }

    @Override
    public List<AbstractNode> getLowerLevel() {
        return this.nodes;
    }

    @Override
    public boolean addNode(AbstractNode node) {
        return this.nodes.add(node);
    }

    @Override
    public boolean removeNode(AbstractNode node) {
        return this.nodes.remove(node);
    }

    @Override
    public void showLevel() {
        System.out.print(this.level);
        for (int i = 0; i < this.level; i++) {
            System.out.print("-");
        }
        System.out.println("树枝");
        showLevel(this.nodes);
    }

    public void showLevel(List<AbstractNode> branches) {
        for (AbstractNode branch : branches) {
            branch.showLevel();
        }
    }
}
