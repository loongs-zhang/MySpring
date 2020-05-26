package com.example.design.iterator.before;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/25
 */
@Getter
@SuppressWarnings("all")
public class Drink {

    private String name;

    private double price;

    private List<String> ingredient;

    private final Drink[] types = new Drink[3];

    public Drink() {
        types[0] = new Drink("奶茶", 6, Arrays.asList("牛奶", "茶叶", "水"));
        types[1] = new Drink("豆浆", 3, Arrays.asList("黄豆", "水"));
        types[2] = new Drink("牛奶", 2.5, Collections.singletonList("牛奶"));
    }

    public Drink(String name, double price, List<String> ingredient) {
        this.name = name;
        this.price = price;
        this.ingredient = ingredient;
    }

}
