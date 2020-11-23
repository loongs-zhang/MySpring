package com.example.design.composite.before;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/27
 */
public class Branch {

    /**
     * 树枝所属的层级
     */
    private final int level;

    /**
     * 树枝集合
     */
    private final List<Branch> branches = new ArrayList<>();

    /**
     * 树叶集合
     */
    private final List<Leaf> leaves = new ArrayList<>();

    public Branch(int level) {
        this.level = level;
    }

    public boolean addBranch(Branch branch) {
        return this.branches.add(branch);
    }

    public boolean removeBranch(Branch branch) {
        return this.branches.remove(branch);
    }

    public boolean addLeaf() {
        return this.leaves.add(new Leaf(level + 1));
    }

    public boolean removeLeaf(Leaf leaf) {
        return this.leaves.remove(leaf);
    }

    /**
     * 展示层级关系
     */
    public void showLevel() {
        System.out.print(this.level);
        for (int i = 0; i < this.level; i++) {
            System.out.print("-");
        }
        System.out.println("树枝");
        showLevel(this.branches);
    }

    public void showLevel(List<Branch> branches) {
        for (Branch branch : branches) {
            branch.showLevel();
        }
        for (Leaf leaf : this.leaves) {
            leaf.showLevel();
        }
    }
}
