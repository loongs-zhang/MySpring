package com.example.design.composite.clear;

/**
 * @author SuccessZhang
 * @date 2020/05/27
 */
public class CompositeAfterTest1 {
    public static void main(String[] args) {
        Branch2 root = new Branch2(1);

        Branch2 branch = new Branch2(2);
        root.addNode(branch);
        root.addNode(new Leaf2(2));

        branch.addNode(new Leaf2(3));
        branch.addNode(new Leaf2(3));

        root.showLevel();
    }
}
