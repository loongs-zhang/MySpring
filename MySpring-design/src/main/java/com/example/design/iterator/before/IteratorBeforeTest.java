package com.example.design.iterator.before;

/**
 * @author SuccessZhang
 * @date 2020/05/25
 */
public class IteratorBeforeTest {

    public static void main(String[] args) {
        Bun bun = new Bun();
        bun.addType("香菇肉包子", 2);
        Drink drink = new Drink();
        printTypes(bun, drink);
    }

    public static void printTypes(Bun bun, Drink drink) {
        for (Bun type : bun.getTypes()) {
            System.out.println(type.getName() + " " + type.getPrice());
        }
        Drink[] types = drink.getTypes();
        for (int i = 0; i < types.length; i++) {
            System.out.println(types[i].getName() + " " + types[i].getPrice() + " " + types[i].getIngredient());
        }
    }

}
