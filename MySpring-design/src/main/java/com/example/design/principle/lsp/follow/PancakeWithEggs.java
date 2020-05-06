package com.example.design.principle.lsp.follow;

import com.example.design.principle.lsp.before.AbstractPancake;
import com.example.design.principle.lsp.before.Egg;
import com.example.design.principle.lsp.before.Pancake;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/06
 */
@SuppressWarnings("unused")
public class PancakeWithEggs extends AbstractPancake {

    private final AbstractPancake pancake;

    private List<Egg> eggs = new ArrayList<>();

    public PancakeWithEggs(Pancake pancake) {
        this.pancake = pancake;
    }

    public void addEgg() {
        eggs.add(new Egg());
    }

    @Override
    public double getPrice() {
        double price = pancake.getPrice();
        for (Egg egg : eggs) {
            price = price + egg.getPrice();
        }
        return price;
    }
}
