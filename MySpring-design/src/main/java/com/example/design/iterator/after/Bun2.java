package com.example.design.iterator.after;

import com.example.design.iterator.before.Bun;

/**
 * @author SuccessZhang
 * @date 2020/05/26
 */
public class Bun2 extends Bun implements Iterable<Bun> {
    @Override
    public Iterator<Bun> iterator() {
        return new SimpleIterator<>(this.getTypes());
    }
}
