package com.example.design.composite.before;

/**
 * @author SuccessZhang
 * @date 2020/05/27
 */
public class CompositeBeforeTest {
    public static void main(String[] args) {
        Branch root = new Branch(1);

        Branch branch = new Branch(2);
        root.addBranch(branch);
        root.addLeaf();

        branch.addLeaf();
        branch.addLeaf();

        root.showLevel();
    }
}
