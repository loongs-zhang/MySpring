package com.dragon.springframework.context.context.annotation;

import com.dragon.springframework.aop.aspectj.AspectJAfterAdvice;
import com.dragon.springframework.aop.aspectj.AspectJAfterReturningAdvice;
import com.dragon.springframework.aop.aspectj.AspectJAfterThrowingAdvice;
import com.dragon.springframework.aop.aspectj.AspectJAroundAdvice;
import com.dragon.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.dragon.springframework.aop.aspectj.AspectJMethodBeforeAdvice;
import com.dragon.springframework.aop.aspectj.AspectJPointcutAdvisor;
import com.dragon.springframework.aop.aspectj.annotation.After;
import com.dragon.springframework.aop.aspectj.annotation.AfterReturning;
import com.dragon.springframework.aop.aspectj.annotation.AfterThrowing;
import com.dragon.springframework.aop.aspectj.annotation.Around;
import com.dragon.springframework.aop.aspectj.annotation.Aspect;
import com.dragon.springframework.aop.aspectj.annotation.Before;
import com.dragon.springframework.aop.aspectj.annotation.Pointcut;
import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.beans.config.ConfigurableBeanFactory;
import com.dragon.springframework.beans.config.ConfigurableListableBeanFactory;
import com.dragon.springframework.beans.factory.BeanFactory;
import com.dragon.springframework.beans.factory.BeanFactoryAware;
import com.dragon.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.dragon.springframework.context.stereotype.Component;
import com.dragon.springframework.core.Ordered;
import com.dragon.springframework.core.StringUtils;
import com.dragon.springframework.core.annotation.AnnotationUtils;
import com.dragon.springframework.core.annotation.Order;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.Method;
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
@Slf4j
@SuppressWarnings("all")
public class ClassPathBeanDefinitionScanner implements BeanFactoryAware {

    private final BeanDefinitionRegistry registry;

    private ConfigurableListableBeanFactory beanFactory;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String... basePackages) {
        Set<BeanDefinition> result = new LinkedHashSet<>();
        for (String basePackage : basePackages) {
            //此时只能拿到字符串
            URL url = this.getClass().getResource("/" + basePackage.replaceAll("\\.", "/"));
            if (url == null) {
                log.warn("url not found!");
                continue;
            }
            File dir = new File(url.getFile());
            File[] files = dir.listFiles();
            if (files == null) {
                log.warn("there is no file!");
                continue;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    doScan(basePackage + "." + file.getName());
                } else {
                    result.addAll(createBeanDefinition(basePackage, file));
                }
            }
        }
        return result;
    }

    private Set<BeanDefinition> createBeanDefinition(String basePackage, File file) {
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        String className = file.getName().replace(".class", "");
        String fullClassName = basePackage + "." + className;
        try {
            Class<?> beanClass = Class.forName(fullClassName);
            if (beanClass.isAnnotation()) {
                return beanDefinitions;
            }
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
                return beanDefinitions;
            }
            factoryBeanName = getFactoryBeanName(beanClass, component);
            BeanDefinition beanDefinition = doCreateBeanDefinition(fullClassName, beanClass, factoryBeanName, autowire, initMethod, destroyMethod, null);
            Class<?>[] interfaces = beanClass.getInterfaces();
            registry.registerBeanDefinition(factoryBeanName, beanDefinition);
            beanDefinitions.add(beanDefinition);
            for (Class<?> i : interfaces) {
                String interfaceName = StringUtils.lowerFirstCase(i.getSimpleName());
                if (!"".equals(interfaceName)) {
                    beanDefinition = doCreateBeanDefinition(fullClassName, beanClass, interfaceName, autowire, initMethod, destroyMethod, null);
                    registry.registerBeanDefinition(interfaceName, beanDefinition);
                    beanDefinitions.add(beanDefinition);
                }
            }
            Aspect aspect = AnnotationUtils.getMergedAnnotation(beanClass, Aspect.class);
            if (aspect != null) {
                beanDefinitions.addAll(handleAspect(beanClass));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beanDefinitions;
    }

    private Set<BeanDefinition> handleAspect(Class<?> beanClass) throws Exception {
        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();
        String pointcutSignature = null;
        Pointcut pointcut = null;
        for (Method method : beanClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Pointcut.class)) {
                pointcutSignature = method.getName() + "()";
                pointcut = AnnotationUtils.getMergedAnnotation(method, Pointcut.class);
                break;
            }
        }
        if (pointcut == null) {
            return beanDefinitions;
        }
        String expression = pointcut.value();
        Component component = AnnotationUtils.getMergedAnnotation(beanClass, Component.class);
        if (component == null) {
            return beanDefinitions;
        }
        String factoryBeanName = getFactoryBeanName(beanClass, component);
        String pointcutName = factoryBeanName + "->" + expression;
        Class<AspectJExpressionPointcut> pointcutClass = AspectJExpressionPointcut.class;
        BeanDefinition pointcutBeanDefinition = doCreateBeanDefinition(pointcutClass.getName(),
                pointcutClass, pointcutName, true, null, null, new Object[]{pointcutName, expression});
        registry.registerBeanDefinition(pointcutBeanDefinition.getFactoryBeanName(), pointcutBeanDefinition);
        beanDefinitions.add(pointcutBeanDefinition);
        for (Method method : beanClass.getDeclaredMethods()) {

            Before before = AnnotationUtils.getMergedAnnotation(method, Before.class);
            Around around = AnnotationUtils.getMergedAnnotation(method, Around.class);
            AfterThrowing afterThrowing = AnnotationUtils.getMergedAnnotation(method, AfterThrowing.class);
            AfterReturning afterReturning = AnnotationUtils.getMergedAnnotation(method, AfterReturning.class);
            After after = AnnotationUtils.getMergedAnnotation(method, After.class);

            boolean[] booleans = initCondition(before, around, afterThrowing, afterReturning, after);
            if (validate(method, booleans)) {
                continue;
            }

            Order order = method.getAnnotation(Order.class);
            int declarationOrder = Ordered.LOWEST_PRECEDENCE;
            if (order != null) {
                declarationOrder = order.value();
            }

            String pointCutOrExpression = getValue(before, around, afterThrowing, afterReturning, after);
            if (pointCutOrExpression.equals(pointcutSignature)) {
                pointCutOrExpression = expression;
            }
            AspectJExpressionPointcut expressionPointcut = beanFactory.getBean(pointcutName, pointcutClass, pointcutName, pointCutOrExpression);

            Class<?> adviceClass = null;
            Object[] initArgs = new Object[]{beanClass, beanFactory.getBean(factoryBeanName), method.getName(), expressionPointcut, declarationOrder};
            if (booleans[0]) {
                adviceClass = AspectJMethodBeforeAdvice.class;
            } else if (booleans[1]) {
                adviceClass = AspectJAroundAdvice.class;
            } else if (booleans[2]) {
                adviceClass = AspectJAfterThrowingAdvice.class;
                String throwing = afterThrowing.throwing();
                initArgs = new Object[]{beanClass, beanFactory.getBean(factoryBeanName), method.getName(), expressionPointcut, declarationOrder, throwing};
            } else if (booleans[3]) {
                adviceClass = AspectJAfterReturningAdvice.class;
                String returning = afterReturning.returning();
                initArgs = new Object[]{returning, beanClass, beanFactory.getBean(factoryBeanName), method.getName(), expressionPointcut, declarationOrder};
            } else if (booleans[4]) {
                adviceClass = AspectJAfterAdvice.class;
            }
            if (adviceClass == null) {
                continue;
            }

            String adviceBeanName = adviceClass.getSimpleName() + "->" + pointCutOrExpression;
            BeanDefinition adviceDefinition = doCreateBeanDefinition(adviceClass.getName(), adviceClass,
                    adviceBeanName, true, null, null, initArgs);
            registry.registerBeanDefinition(adviceDefinition.getFactoryBeanName(), adviceDefinition);
            beanDefinitions.add(adviceDefinition);

            Class<AspectJPointcutAdvisor> advisorClass = AspectJPointcutAdvisor.class;
            BeanDefinition advisorDefinition = doCreateBeanDefinition(advisorClass.getName(), advisorClass,
                    method.getName() + "->" + pointCutOrExpression, true, null, null,
                    new Object[]{beanFactory.getBean(adviceBeanName, adviceClass), expressionPointcut});
            registry.registerBeanDefinition(advisorDefinition.getFactoryBeanName(), advisorDefinition);
            beanDefinitions.add(advisorDefinition);
        }
        return beanDefinitions;
    }

    private boolean[] initCondition(Before before, Around around, AfterThrowing afterThrowing, AfterReturning afterReturning, After after) {
        boolean[] booleans = new boolean[5];
        booleans[0] = before != null;
        booleans[1] = around != null;
        booleans[2] = afterThrowing != null;
        booleans[3] = afterReturning != null;
        booleans[4] = after != null;
        return booleans;
    }

    private boolean validate(Method method, boolean[] booleans) {
        int count = 0;
        for (boolean hasAnnotation : booleans) {
            if (hasAnnotation) {
                count++;
            }
        }
        if (count != 1) {
            log.error("the method:{} has more than 1 advice annotation !", method.toString());
            return true;
        }
        return false;
    }

    private String getValue(Before before, Around around, AfterThrowing afterThrowing, AfterReturning afterReturning, After after) {
        String pointCutOrExpression = null;
        if (before != null) {
            pointCutOrExpression = before.value();
        } else if (around != null) {
            pointCutOrExpression = around.value();
        } else if (afterThrowing != null) {
            pointCutOrExpression = afterThrowing.value();
        } else if (afterReturning != null) {
            pointCutOrExpression = afterReturning.value();
        } else if (after != null) {
            pointCutOrExpression = after.value();
        }
        return pointCutOrExpression;
    }

    private String getFactoryBeanName(Class<?> beanClass, Component component) {
        String factoryBeanName = component.value();
        if ("".equals(factoryBeanName)) {
            factoryBeanName = StringUtils.lowerFirstCase(beanClass.getSimpleName());
        }
        return factoryBeanName;
    }

    private BeanDefinition doCreateBeanDefinition(String beanClassName, Class<?> beanClass, String beanName, boolean autowire, String initMethodName, String destroyMethodName, Object[] initArguments) {
        String scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON;
        Scope scope = AnnotationUtils.getMergedAnnotation(beanClass, Scope.class);
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
                .initArguments(initArguments)
                .build();
        Lazy lazy = AnnotationUtils.getMergedAnnotation(beanClass, Lazy.class);
        if (lazy != null) {
            beanDefinition.setLazyInit(lazy.value());
        }
        return beanDefinition;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws Exception {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }
}
