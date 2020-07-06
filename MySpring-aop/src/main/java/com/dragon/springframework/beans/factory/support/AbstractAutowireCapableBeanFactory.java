package com.dragon.springframework.beans.factory.support;

import com.dragon.springframework.beans.BeanWrapper;
import com.dragon.springframework.beans.config.BeanDefinition;
import com.dragon.springframework.beans.config.BeanPostProcessor;
import com.dragon.springframework.beans.config.ConfigurableBeanFactory;
import com.dragon.springframework.beans.factory.Aware;
import com.dragon.springframework.beans.factory.BeanClassLoaderAware;
import com.dragon.springframework.beans.factory.BeanFactory;
import com.dragon.springframework.beans.factory.BeanFactoryAware;
import com.dragon.springframework.beans.factory.BeanNameAware;
import com.dragon.springframework.beans.factory.FactoryBean;
import com.dragon.springframework.beans.factory.InitializingBean;
import com.dragon.springframework.beans.factory.annotation.Autowired;
import com.dragon.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.dragon.springframework.context.context.annotation.Lazy;
import com.dragon.springframework.core.ClassUtils;
import com.dragon.springframework.core.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供Bean创建（带有构造函数解析），属性填充，
 * 接线（包括自动装配）和初始化。处理运行时bean引用，
 * 解析托管集合，调用初始化方法等。支持自动装配构造函数、
 * 名称（属性）和类型（属性）。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class AbstractAutowireCapableBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, AutowireCapableBeanFactory {

    private final Set<BeanPostProcessor> beanPostProcessors = new HashSet<>();

    /**
     * 未完成的FactoryBean实例的缓存: FactoryBean name --> BeanWrapper
     */
    private final Map<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>(16);

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
        if (beanDefinition == null) {
            return this.getBean(beanName, (Object[]) null);
        }
        return this.getBean(beanName, beanDefinition.getBeanClass(), beanDefinition.getInitArguments());
    }

    @Override
    public Object getBean(String beanName, Object... args) throws Exception {
        return this.getBean(beanName, null, args);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws Exception {
        String beanName = StringUtils.lowerFirstCase(requiredType.getSimpleName());
        BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
        if (beanDefinition == null) {
            return this.getBean(requiredType, (Object[]) null);
        }
        return this.getBean(beanName, requiredType, beanDefinition.getInitArguments());
    }

    @Override
    public <T> T getBean(Class<T> requiredType, Object... args) throws Exception {
        return this.getBean(StringUtils.lowerFirstCase(requiredType.getSimpleName()), requiredType, args);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType) throws Exception {
        BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
        if (beanDefinition == null) {
            return this.getBean(beanName, requiredType, (Object[]) null);
        }
        return this.getBean(beanName, requiredType, beanDefinition.getInitArguments());
    }

    @Override
    public <T> T getBean(String beanName, Class<T> requiredType, Object... args) throws Exception {
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        if (beanDefinition == null) {
            return null;
        }
        Object bean = getSingleton(beanName);
        if (bean == null) {
            bean = createBean(beanName, beanDefinition, args);
        }
        if (beanDefinition.isSingleton()) {
            registerSingleton(beanName, bean);
        }
        if (requiredType == null || requiredType.isInstance(bean)) {
            return (T) bean;
        }
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return super.containsSingleton(name) ||
                factoryBeanInstanceCache.containsKey(name);
    }

    @Override
    public boolean isSingleton(String name) throws Exception {
        if (name == null) {
            return false;
        }
        Object singleton = super.getSingleton(name);
        if (singleton == null) {
            return this.getBeanDefinition(name).isSingleton();
        }
        if (singleton instanceof FactoryBean) {
            return name.startsWith(BeanFactory.FACTORY_BEAN_PREFIX) || ((FactoryBean) singleton).isSingleton();
        }
        return !name.startsWith(BeanFactory.FACTORY_BEAN_PREFIX);
    }

    @Override
    public boolean isPrototype(String name) throws Exception {
        if (name == null) {
            return false;
        }
        return !this.isSingleton(name);
    }

    @Override
    public Class<?> getType(String name) throws Exception {
        return super.getSingleton(name).getClass();
    }

    @Override
    public <T> T createBean(Class<T> beanClass) throws Exception {
        String factoryBeanName = StringUtils.lowerFirstCase(beanClass.getSimpleName());
        BeanDefinition beanDefinition = BeanDefinition.builder()
                .lazyInit(false)
                .scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
                .factoryBeanName(factoryBeanName)
                .beanClassName(beanClass.getName())
                .beanClass(beanClass)
                .autowire(true)
                .build();
        Lazy lazy = beanClass.getAnnotation(Lazy.class);
        if (lazy != null) {
            beanDefinition.setLazyInit(lazy.value());
        }
        return (T) createBean(factoryBeanName, beanDefinition, null);
    }

    @Override
    public Object initializeBean(Object existingBean, String beanName) throws Exception {
        return initializeBean(beanName, existingBean, this.getBeanDefinition(beanName));
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws Exception {
        Object result = existingBean;
        // 遍历容器为所创建的Bean添加的所有BeanPostProcessor后置处理器
        for (BeanPostProcessor beanProcessor : this.beanPostProcessors) {
            // 调用Bean实例所有的后置处理中的初始化前处理方法，
            // 为Bean实例对象在初始化之前做一些自定义的处理操作
            Object current = beanProcessor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws Exception {
        Object result = existingBean;
        //遍历容器为所创建的Bean添加的所有BeanPostProcessor后置处理器
        for (BeanPostProcessor beanProcessor : this.beanPostProcessors) {
            // 调用Bean实例所有的后置处理中的初始化后处理方法，
            // 为Bean实例对象在初始化之后做一些自定义的处理操作
            Object current = beanProcessor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public void destroyBean(Object existingBean) {
        new DisposableBeanAdapter(existingBean, this.beanPostProcessors, AccessController.getContext())
                .destroy();
    }

    @Override
    public void destroySingletons() {
        super.destroySingletons();
    }

    @Override
    protected void removeSingleton(String beanName) {
        super.removeSingleton(beanName);
        this.factoryBeanInstanceCache.remove(beanName);
    }

    protected Object createBean(String beanName, BeanDefinition bd, Object[] args) throws Exception {
        BeanWrapper instanceWrapper = this.factoryBeanInstanceCache.get(beanName);
        if (instanceWrapper == null) {
            instanceWrapper = createBeanInstance(beanName, bd, args);
            this.factoryBeanInstanceCache.put(beanName, instanceWrapper);
            Object bean = instanceWrapper.getWrappedObject();
            try {
                populateBean(beanName, bd, instanceWrapper);
                bean = initializeBean(beanName, bean, bd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                registerDisposableBeanIfNecessary(beanName, bean, bd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            instanceWrapper.setWrappedObject(bean);
            instanceWrapper.setWrappedClass(bean.getClass());
        }
        return instanceWrapper.getWrappedObject();
    }

    private BeanWrapper createBeanInstance(String beanName, BeanDefinition beanDefinition, Object... args) throws Exception {
        Class<?> beanClass = beanDefinition.getBeanClass();
        if (args == null) {
            return instantiateBean(beanName, beanDefinition);
        }
        Class<?>[] argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        for (Constructor<?> constructor : beanClass.getConstructors()) {
            Class<?>[] types = constructor.getParameterTypes();
            if (argTypes.length != types.length) {
                continue;
            }
            boolean autowired = true;
            for (int i = 0; i < argTypes.length; i++) {
                Class<?> type = types[i];
                Class<?> resolvedPrimitive = ClassUtils.getResolvedPrimitive(argTypes[i]);
                if (!argTypes[i].equals(type) &&
                        resolvedPrimitive != null &&
                        !resolvedPrimitive.equals(type)) {
                    autowired = false;
                    break;
                }
            }
            if (autowired) {
                return autowireConstructor(beanName, beanDefinition, constructor, args);
            }
        }
        return null;
    }

    private BeanWrapper instantiateBean(final String beanName, final BeanDefinition bd) {
        try {
            Object instance;
            Class<?> beanClass = bd.getBeanClass();
            Constructor<?> constructor = beanClass.getConstructor();
            if (bd.isSingleton()) {
                instance = super.getSingleton(beanName);
                if (instance == null) {
                    instance = constructor.newInstance();
                    super.registerSingleton(beanName, instance);
                }
            } else {
                instance = constructor.newInstance();
            }
            return new BeanWrapper(instance, beanClass, instance, beanClass);
        } catch (Exception e) {
            return null;
        }
    }

    private BeanWrapper autowireConstructor(String beanName, BeanDefinition beanDefinition, Constructor<?> constructor, Object[] explicitArgs) {
        try {
            Object instance;
            Class<?> beanClass = beanDefinition.getBeanClass();
            if (beanDefinition.isSingleton()) {
                instance = super.getSingleton(beanName);
                if (instance == null) {
                    instance = constructor.newInstance(explicitArgs);
                    super.registerSingleton(beanName, instance);
                }
            } else {
                instance = constructor.newInstance(explicitArgs);
            }
            return new BeanWrapper(instance, beanClass, instance, beanClass);
        } catch (Exception e) {
            return null;
        }
    }

    private void populateBean(String beanName, BeanDefinition beanDefinition, BeanWrapper beanWrapper) throws Exception {
        Object instance = beanWrapper.getRootObject();
        Class<?> beanClass = beanDefinition.getBeanClass();
        Field[] fields = beanClass.getDeclaredFields();
        for (Field field : fields) {
            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired == null) {
                continue;
            }
            String autowiredBeanName = autowired.value().trim();
            Class<?> fieldType = field.getType();
            if ("".equals(autowiredBeanName)) {
                autowiredBeanName = StringUtils.lowerFirstCase(fieldType.getSimpleName());
            }
            field.setAccessible(true);
            field.set(instance, getBean(autowiredBeanName, fieldType));
        }
    }

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        invokeAwareMethods(beanName, bean);
        bean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        try {
            bean = invokeInitMethods(beanName, bean, beanDefinition);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return bean;
    }

    private void invokeAwareMethods(final String beanName, final Object bean) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ClassLoader bcl = ClassLoader.getSystemClassLoader();
                if (bcl != null) {
                    ((BeanClassLoaderAware) bean).setBeanClassLoader(bcl);
                }
            }
            if (bean instanceof BeanFactoryAware) {
                try {
                    ((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
                } catch (Exception ignored) {
                }
            }
        }
    }

    private Object invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Throwable {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        String initMethodName = beanDefinition.getInitMethodName();
        if (!beanDefinition.isAutowire() && !StringUtils.isEmpty(initMethodName)) {
            //不自动装配，调用指定的init方法
            Method method = bean.getClass().getDeclaredMethod(initMethodName);
            bean = method.invoke(bean);
        }
        return bean;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition mbd) {
        if (mbd.isSingleton()) {
            registerDisposableBean(beanName,
                    new DisposableBeanAdapter(bean, beanName, mbd, this.beanPostProcessors,
                            AccessController.getContext()));
        }
    }

    /**
     * 返回给定bean名称的bean定义。子类通常应实现缓存，
     * 因为每次需要bean定义元数据时，此类便会调用此方法。
     * 根据具体bean工厂实现的性质，此操作可能很昂贵，
     * 但是对于可列出的bean工厂，这通常只相当于本地哈希查找，
     * 因此，该操作是其中公共接口的一部分。
     * 在这种情况下，此模板方法和公共接口方法都可以使用相同的实现。
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws Exception;
}