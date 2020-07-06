package com.dragon.springframework.aop.framework;

/**
 * 代理工厂的基类，提供对可配置AopProxyFactory的便捷访问。
 *
 * @author SuccessZhang
 * @date 2020/07/05
 */
public class ProxyCreatorSupport extends AdvisedSupport implements AopProxyFactory {

    /**
     * 子类应调用此方法以获得新的AOP代理，
     * 他们不应该使用{@code this}作为参数来创建AOP代理。
     */
    protected final AopProxy createAopProxy() {
        return createAopProxy(this);
    }

    @Override
    public final synchronized AopProxy createAopProxy(AdvisedSupport config) {
        if (config.getInterfaces().isEmpty()) {
            return new CglibAopProxy(config);
        }
        return new JdkDynamicAopProxy(config);
    }
}
