package com.example.design.composite.after.safe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/27
 */
public class Branch3 extends AbstractNode2 {

    private final List<AbstractNode2> nodes = new ArrayList<>();

    public Branch3(int level) {
        super(level);
    }

    public List<AbstractNode2> getLowerLevel() {
        return this.nodes;
    }

    public boolean addNode(AbstractNode2 node) {
        return this.nodes.add(node);
    }

    public boolean removeNode(AbstractNode2 node) {
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

    public void showLevel(List<AbstractNode2> branches) {
        for (AbstractNode2 branch : branches) {
            branch.showLevel();
        }
    }
}
