package com.example.design.composite.after.safe;

/**
 * @author SuccessZhang
 * @date 2020/05/27
 */
public class CompositeAfterTest2 {
    public static void main(String[] args) {
        Branch3 root = new Branch3(1);

        Branch3 branch = new Branch3(2);
        root.addNode(branch);
        root.addNode(new Leaf3(2));

        branch.addNode(new Leaf3(3));
        branch.addNode(new Leaf3(3));

        root.showLevel();
    }
}
