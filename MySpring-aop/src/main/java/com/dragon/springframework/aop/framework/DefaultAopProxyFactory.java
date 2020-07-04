package com.dragon.springframework.aop.framework;

/**
 * @author SuccessZhang
 * @date 2020/07/03
 */
public class DefaultAopProxyFactory {

    public AopProxy createAopProxy(AdvisedSupport config) {
        if (config.getInterfaces().isEmpty()) {
            return new CglibAopProxy(config);
        }
        return new JdkDynamicAopProxy(config);
    }

}
