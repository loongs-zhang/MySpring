package com.dragon.springframework.context.context.annotation;

import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.beans.config.ConfigurableBeanFactory;
import com.dragon.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.dragon.springframework.context.stereotype.Component;
import com.dragon.springframework.core.StringUtils;
import com.dragon.springframework.core.annotation.AnnotationUtils;

import java.io.File;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 一个bean定义扫描器，它检测类路径上的bean候选者，
 * 用给定的注册表注册相应的bean定义。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
public class ClassPathBeanDefinitionScanner {

    private final BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String... basePackages) {
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            //此时只能拿到字符串
            URL url = this.getClass().getResource("/" + basePackage.replaceAll("\\.", "/"));
            if (url == null) {
                throw new RuntimeException("url not found!");
            }
            File dir = new File(url.getFile());
            File[] files = dir.listFiles();
            if (files == null) {
                throw new RuntimeException("there is no file!");
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    doScan(basePackage + "." + file.getName());
                } else {
                    String className = file.getName().replace(".class", "");
                    String fullClassName = basePackage + "." + className;
                    try {
                        Class<?> beanClass = Class.forName(fullClassName);
                        String factoryBeanName = "";
                        boolean autowire = true;
                        String initMethod = null;
                        String destroyMethod = null;
                        Bean bean = AnnotationUtils.getMergedAnnotation(beanClass, Bean.class);
                        if (bean != null) {
                            autowire = bean.autowire();
                            initMethod = bean.initMethod();
                            destroyMethod = bean.destroyMethod();
                        }
                        Component component = AnnotationUtils.getMergedAnnotation(beanClass, Component.class);
                        if (component == null) {
                            continue;
                        }
                        factoryBeanName = component.value();
                        if ("".equals(factoryBeanName)) {
                            factoryBeanName = StringUtils.lowerFirstCase(beanClass.getSimpleName());
                        }
                        BeanDefinition beanDefinition = doCreateBeanDefinition(fullClassName, beanClass,
                                factoryBeanName, autowire, initMethod, destroyMethod);
                        Class<?>[] interfaces = beanClass.getInterfaces();
                        registry.registerBeanDefinition(factoryBeanName, beanDefinition);
                        beanDefinitions.add(beanDefinition);
                        for (Class<?> i : interfaces) {
                            String interfaceName = StringUtils.lowerFirstCase(i.getSimpleName());
                            if (!"".equals(interfaceName)) {
                                beanDefinition = doCreateBeanDefinition(fullClassName, beanClass,
                                        interfaceName, autowire, initMethod, destroyMethod);
                                registry.registerBeanDefinition(interfaceName, beanDefinition);
                                beanDefinitions.add(beanDefinition);
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return beanDefinitions;
    }

    private BeanDefinition doCreateBeanDefinition(String beanClassName, Class<?> beanClass, String beanName, boolean autowire, String initMethodName, String destroyMethodName) {
        String scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON;
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (scope != null) {
            scopeName = scope.scopeName();
        }
        switch (scopeName) {
            case ConfigurableBeanFactory.SCOPE_SINGLETON:
            case ConfigurableBeanFactory.SCOPE_PROTOTYPE:
                break;
            default:
                throw new RuntimeException("wrong bean scope !");
        }
        BeanDefinition beanDefinition = BeanDefinition.builder()
                .lazyInit(false)
                .scope(scopeName)
                .factoryBeanName(beanName)
                .beanClassName(beanClassName)
                .beanClass(beanClass)
                .autowire(autowire)
                .initMethodName(initMethodName)
                .destroyMethodName(destroyMethodName)
                .build();
        Lazy lazy = beanClass.getAnnotation(Lazy.class);
        if (lazy != null) {
            beanDefinition.setLazyInit(lazy.value());
        }
        return beanDefinition;
    }

}
