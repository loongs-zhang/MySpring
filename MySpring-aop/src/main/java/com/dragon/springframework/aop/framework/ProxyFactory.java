package com.dragon.springframework.aop.framework;

import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @author SuccessZhang
 * @date 2020/07/03
 */
@NoArgsConstructor
public class ProxyFactory extends AdvisedSupport {

    public ProxyFactory(Object target) {
        setTarget(target);
        Class<?> targetClass = target.getClass();
        setTargetClass(targetClass);
        setInterfaces(Arrays.asList(targetClass.getInterfaces()));
    }

    /**
     * Create a new proxy according to the settings in this factory.
     * <p>Can be called repeatedly. Effect will vary if we've added
     * or removed interfaces. Can add and remove interceptors.
     * <p>Uses a default class loader: Usually, the thread context class loader
     * (if necessary for proxy creation).
     *
     * @return the proxy object
     */
    public Object getProxy() {
        return createAopProxy(this).getProxy();
    }

    /**
     * Create a new proxy according to the settings in this factory.
     * <p>Can be called repeatedly. Effect will vary if we've added
     * or removed interfaces. Can add and remove interceptors.
     * <p>Uses the given class loader (if necessary for proxy creation).
     *
     * @param classLoader the class loader to create the proxy with
     *                    (or {@code null} for the low-level proxy facility's default)
     * @return the proxy object
     */
    public Object getProxy(ClassLoader classLoader) {
        return createAopProxy(this).getProxy(classLoader);
    }

    /***/
    private AopProxy createAopProxy(AdvisedSupport config) {
        if (config.getInterfaces().isEmpty()) {
            return new CglibAopProxy(config);
        }
        return new JdkDynamicAopProxy(config);
    }
}
