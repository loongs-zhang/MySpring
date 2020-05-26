package com.example.design.iterator.after;

import com.example.design.iterator.before.Bun;
import com.example.design.iterator.before.Drink;

/**
 * @author SuccessZhang
 * @date 2020/05/26
 */
public class IteratorAfterTest {

    public static void main(String[] args) {
        Bun2 bun = new Bun2();
        bun.addType("香菇肉包子", 2);
        Drink2 drink = new Drink2();
        printTypes(bun, drink);
    }

    public static void printTypes(Bun2 bun, Drink2 drink) {
        Iterator<Bun> buns = bun.iterator();
        while (buns.hasNext()) {
            Bun type = buns.next();
            System.out.println(type.getName() + " " + type.getPrice());
        }
        Iterator<Drink> drinks = drink.iterator();
        while (drinks.hasNext()) {
            Drink type = drinks.next();
            System.out.println(type.getName() + " " + type.getPrice() + " " + type.getIngredient());
        }
    }

}
