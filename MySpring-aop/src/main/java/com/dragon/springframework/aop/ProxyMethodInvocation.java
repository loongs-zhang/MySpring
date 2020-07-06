package com.dragon.springframework.aop;

import com.dragon.springframework.aop.intercept.MethodInvocation;

/**
 * {@link MethodInvocation}接口的扩展，
 * 允许访问通过其进行方法调用的代理。
 *
 * @author SuccessZhang
 * @date 2020/06/30
 */
public interface ProxyMethodInvocation extends MethodInvocation {

    /**
     * 返回进行此方法调用的代理。
     */
    Object getProxy();

    /**
     * 创建此对象的副本，如果在此对象上调用{@link #proceed()}
     * 之前完成克隆，则每个克隆可以调用{@link #proceed()}一次，
     * 这样就可以多次调用连接点（以及通知链的其余部分）。
     */
    MethodInvocation invocableClone();

    /**
     * 创建此对象的副本，如果在此对象上调用{@link #proceed()}
     * 之前完成克隆，则每个克隆可以调用{@link #proceed()}一次，
     * 这样就可以多次调用连接点（以及通知链的其余部分）。
     */
    MethodInvocation invocableClone(Object... arguments);

    /**
     * 在该链中的任何通知里设置在后续调用中使用的参数。
     */
    void setArguments(Object... arguments);

    /**
     * 将具有给定值的指定用户属性添加到此调用。
     * 它们作为调用对象的一部分保留，以用于特殊的拦截器。
     */
    void setUserAttribute(String key, Object value);

    /**
     * 返回指定用户属性的值。
     */
    Object getUserAttribute(String key);

}
