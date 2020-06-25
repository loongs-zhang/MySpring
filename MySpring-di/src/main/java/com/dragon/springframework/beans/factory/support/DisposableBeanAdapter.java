package com.dragon.springframework.beans.factory.support;

import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.beans.config.BeanPostProcessor;
import com.dragon.springframework.beans.factory.DisposableBean;
import com.dragon.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import com.dragon.springframework.core.Assert;
import com.dragon.springframework.core.ReflectionUtils;
import com.dragon.springframework.core.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 实现{@link DisposableBean}和{@link Runnable}接口的适配器，
 * 在给定的bean实例上执行各种销毁步骤。
 *
 * @author SuccessZhang
 * @date 2020/06/12
 */
@Slf4j
@SuppressWarnings("unused")
class DisposableBeanAdapter implements DisposableBean, Runnable, Serializable {

    private final Object bean;

    private final String beanName;

    private final boolean invokeDisposableBean;

    private final AccessControlContext acc;

    private String destroyMethodName;

    private transient Method destroyMethod;

    private List<DestructionAwareBeanPostProcessor> beanPostProcessors;

    DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition,
                          Set<BeanPostProcessor> postProcessors, AccessControlContext acc) {

        Assert.notNull(bean, "Disposable bean must not be null");
        this.bean = bean;
        this.beanName = beanName;
        this.invokeDisposableBean = (this.bean instanceof DisposableBean);
        this.acc = acc;
        String destroyMethodName = inferDestroyMethodIfNecessary(bean, beanDefinition);
        if (destroyMethodName != null && !(this.invokeDisposableBean && "destroy".equals(destroyMethodName))) {
            this.destroyMethodName = destroyMethodName;
            this.destroyMethod = determineDestroyMethod(destroyMethodName);
            if (this.destroyMethod == null) {
                throw new RuntimeException("Couldn't find a destroy method named '" +
                        destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            Class<?>[] paramTypes = this.destroyMethod.getParameterTypes();
            if (paramTypes.length > 1) {
                throw new RuntimeException("Method '" + destroyMethodName + "' of bean '" +
                        beanName + "' has more than one parameter - not supported as destroy method");
            } else if (paramTypes.length == 1 && boolean.class != paramTypes[0]) {
                throw new RuntimeException("Method '" + destroyMethodName + "' of bean '" +
                        beanName + "' has a non-boolean parameter - not supported as destroy method");
            }
        }
        this.beanPostProcessors = filterPostProcessors(postProcessors, bean);
    }

    private String inferDestroyMethodIfNecessary(Object bean, BeanDefinition beanDefinition) {
        String destroyMethodName = beanDefinition.getDestroyMethodName();
        if (destroyMethodName == null && bean instanceof AutoCloseable) {
            if (!(bean instanceof DisposableBean)) {
                try {
                    return bean.getClass().getMethod("close").getName();
                } catch (NoSuchMethodException ex) {
                    try {
                        return bean.getClass().getMethod("shutdown").getName();
                    } catch (NoSuchMethodException ex2) {
                    }
                }
            }
            return null;
        }
        return (StringUtils.hasText(destroyMethodName) ? destroyMethodName : null);
    }

    private Method determineDestroyMethod(String name) {
        for (Method declaredMethod : bean.getClass().getDeclaredMethods()) {
            if (declaredMethod.getName().equals(name)) {
                return declaredMethod;
            }
        }
        return null;
    }

    DisposableBeanAdapter(Object bean, Set<BeanPostProcessor> postProcessors, AccessControlContext acc) {
        this.bean = bean;
        this.beanName = bean.getClass().getName();
        this.invokeDisposableBean = (this.bean instanceof DisposableBean);
        this.acc = acc;
        this.beanPostProcessors = filterPostProcessors(postProcessors, bean);
    }

    private List<DestructionAwareBeanPostProcessor> filterPostProcessors(Set<BeanPostProcessor> processors, Object bean) {
        List<DestructionAwareBeanPostProcessor> filteredPostProcessors = null;
        if (processors != null && !processors.isEmpty()) {
            filteredPostProcessors = new ArrayList<>(processors.size());
            for (BeanPostProcessor processor : processors) {
                if (processor instanceof DestructionAwareBeanPostProcessor) {
                    DestructionAwareBeanPostProcessor dabpp = (DestructionAwareBeanPostProcessor) processor;
                    if (dabpp.requiresDestruction(bean)) {
                        filteredPostProcessors.add(dabpp);
                    }
                }
            }
        }
        return filteredPostProcessors;
    }

    @Override
    public void destroy() {
        for (DestructionAwareBeanPostProcessor processor : this.beanPostProcessors) {
            try {
                processor.postProcessBeforeDestruction(this.bean, this.beanName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (this.invokeDisposableBean) {
            try {
                if (System.getSecurityManager() != null) {
                    AccessController.doPrivileged((PrivilegedExceptionAction<Object>) () -> {
                        ((DisposableBean) bean).destroy();
                        return null;
                    }, acc);
                } else {
                    ((DisposableBean) bean).destroy();
                }
            } catch (Throwable ex) {
                String msg = "Invocation of destroy method failed on bean with name '" + this.beanName + "'";
                log.warn(msg + ": " + ex);
            }
        }

        if (this.destroyMethod != null) {
            invokeCustomDestroyMethod(this.destroyMethod);
        } else if (this.destroyMethodName != null) {
            Method methodToCall = determineDestroyMethod(this.destroyMethodName);
            if (methodToCall != null) {
                invokeCustomDestroyMethod(methodToCall);
            }
        }

    }

    private void invokeCustomDestroyMethod(Method destroyMethod) {
        Class<?>[] paramTypes = destroyMethod.getParameterTypes();
        final Object[] args = new Object[paramTypes.length];
        if (paramTypes.length == 1) {
            args[0] = Boolean.TRUE;
        }
        ReflectionUtils.makeAccessible(destroyMethod);
        try {
            destroyMethod.invoke(bean, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void run() {
        destroy();
    }
}
