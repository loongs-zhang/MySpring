package com.example.design.principle.lsp.before;

/**
 * @author SuccessZhang
 * @date 2020/05/06
 */
public class PancakeWithOneEgg extends Pancake {
    @Override
    public double getPrice() {
        return super.getPrice() + new Egg().getPrice();
    }
}
