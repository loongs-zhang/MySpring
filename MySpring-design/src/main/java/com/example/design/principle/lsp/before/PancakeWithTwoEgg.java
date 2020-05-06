package com.example.design.principle.lsp.before;

/**
 * @author SuccessZhang
 * @date 2020/05/06
 */
@SuppressWarnings("unused")
public class PancakeWithTwoEgg extends PancakeWithOneEgg {
    @Override
    public double getPrice() {
        return super.getPrice() + new Egg().getPrice();
    }
}
