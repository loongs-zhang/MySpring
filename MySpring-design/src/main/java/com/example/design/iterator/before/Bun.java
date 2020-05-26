package com.example.design.iterator.before;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/25
 */
@Getter
@SuppressWarnings("all")
public class Bun {

    private String name;

    private double price;

    private final List<Bun> types = new ArrayList<>();

    public Bun() {
        types.addAll(Arrays.asList(new Bun("鲜肉包子", 1.5),
                new Bun("香菇青菜包子", 1),
                new Bun("鱼香肉丝包子", 2)));
    }

    public Bun(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void addType(String name, double price) {
        types.add(new Bun(name, price));
    }

}
