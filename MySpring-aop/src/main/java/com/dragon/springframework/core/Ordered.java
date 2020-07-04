package com.dragon.springframework.core;

/**
 * {@code Ordered}应该由可排序的对象实现的接口，
 * 例如在{@code Collection}中，实际的{@link #getOrder}
 * 可以被解释为优先级，第一个对象（具有最低值）具有最高的优先级。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
public interface Ordered {

    /**
     * 最高优先级值的有用常数。
     */
    int HIGHEST_PRECEDENCE = Integer.MIN_VALUE;

    /**
     * 最低优先级值的有用常数。
     */
    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    /**
     * 获取此对象的排序值。
     */
    int getOrder();

}
