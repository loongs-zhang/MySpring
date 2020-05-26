package com.example.design.iterator.after;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SuccessZhang
 * @date 2020/05/25
 */
public class SimpleIterator<T> implements Iterator<T> {

    private int current = 0;

    private List<T> list;

    public SimpleIterator(List<T> list) {
        this.list = new ArrayList<>(list);
    }

    @Override
    public boolean hasNext() {
        return current < list.size();
    }

    @Override
    public T next() {
        return list.get(current++);
    }
}
