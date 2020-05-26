package com.example.design.iterator.after;

/**
 * @author SuccessZhang
 * @date 2020/05/25
 */
public interface Iterable<T> {

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    Iterator<T> iterator();

}
