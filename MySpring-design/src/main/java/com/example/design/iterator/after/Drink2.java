package com.example.design.iterator.after;

import com.example.design.iterator.before.Drink;

import java.util.Arrays;

/**
 * @author SuccessZhang
 * @date 2020/05/26
 */
public class Drink2 extends Drink implements Iterable<Drink> {
    @Override
    public Iterator<Drink> iterator() {
        return new SimpleIterator<>(Arrays.asList(this.getTypes()));
    }
}
