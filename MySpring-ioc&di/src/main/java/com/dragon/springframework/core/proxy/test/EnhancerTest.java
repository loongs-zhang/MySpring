package com.dragon.springframework.core.proxy.test;

import com.dragon.springframework.core.proxy.cglib.Enhancer;
import com.dragon.springframework.core.proxy.cglib.MethodInterceptor;
import com.dragon.springframework.core.proxy.cglib.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author SuccessZhang
 * @date 2020/05/13
 */
public class EnhancerTest implements MethodInterceptor {

    @SuppressWarnings("unchecked")
    public static <T> T getHelpedGraduate(final Object target) {
        return (T) Enhancer.create(target.getClass(), new EnhancerTest());
    }

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("收集各个公司的人才需求");
        Object result = proxy.invokeSuper(object, args);
        System.out.println("需求匹配，帮忙找到一份A公司的工作");
        return result;
    }

    public static void main(String[] args) {
        //使用cglib，Graduate不用实现任何接口
        Graduate graduate = new Graduate();
        graduate = EnhancerTest.getHelpedGraduate(graduate);
        graduate.findJob();
    }
}
