package com.example.design.composite.clear;

/**
 * @author SuccessZhang
 * @date 2020/05/27
 */
public class Leaf2 extends AbstractNode {

    public Leaf2(int level) {
        super(level);
    }

    @Override
    public void showLevel() {
        System.out.print(super.level);
        for (int i = 0; i < super.level; i++) {
            System.out.print("-");
        }
        System.out.println("叶子");
    }
}
