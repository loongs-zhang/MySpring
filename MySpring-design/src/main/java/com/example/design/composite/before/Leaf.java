package com.example.design.composite.before;

/**
 * @author SuccessZhang
 * @date 2020/05/27
 */
public class Leaf {

    /**
     * 树叶所属的层级
     */
    private final int level;

    public Leaf(int level) {
        this.level = level;
    }

    public void showLevel() {
        System.out.print(this.level);
        for (int i = 0; i < this.level; i++) {
            System.out.print("-");
        }
        System.out.println("叶子");
    }
}
