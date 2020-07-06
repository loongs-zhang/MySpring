package com.dragon.springframework.aop.framework;

import com.dragon.springframework.aop.Advisor;
import com.dragon.springframework.aop.intercept.MethodInterceptor;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AOP代理配置管理器的基类，本身不是AOP代理，
 * 但此类的子类通常直接从中获得AOP代理实例的工厂
 * （作者改为了直接从子类本身获取）。
 * 此类解放了子类对于Advices和Advisor的管理，
 * 用于保存代理的快照，但并没有实现创建代理的方法。
 *
 * @author SuccessZhang
 * @date 2020/07/03
 */
@Data
public class AdvisedSupport {

    private final DefaultAdvisorChainFactory advisorChainFactory = new DefaultAdvisorChainFactory();

    /**
     * 被代理的bean实例。
     */
    private Object target;

    /**
     * 被代理bean的类，后续创建CGLIB代理使用。
     */
    private Class<?> targetClass;

    private Map<Method, List<Object>> methodCache = new ConcurrentHashMap<>(32);

    /**
     * 代理要实现的接口，后续创建JDK代理使用。
     */
    private List<Class<?>> interfaces;

    private List<Advisor> advisors = new LinkedList<>();

    public void addAdvisors(Advisor... advisors) {
        addAdvisors(Arrays.asList(advisors));
    }

    public void addAdvisors(Collection<Advisor> advisors) {
        this.advisors.addAll(advisors);
    }

    /**
     * 确定给定方法的通知实例（{@link MethodInterceptor}的实现）列表。
     */
    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        List<Object> cached = this.methodCache.get(method);
        if (cached == null) {
            cached = this.advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(this, method, targetClass);
            this.methodCache.put(method, cached);
        }
        return cached;
    }
}
