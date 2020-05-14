package com.example.design.proxy.dynamicproxy.jdk;

import com.example.design.proxy.staticproxy.Graduate;
import com.example.design.proxy.staticproxy.Worker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author SuccessZhang
 * @date 2020/05/13
 */
public class Headhunter2 implements InvocationHandler {

    private final Object target;

    public Headhunter2(Object target) {
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getHelpedWorker(final Object target) {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), new Headhunter2(target));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("收集各个公司的人才需求");
        Object result = method.invoke(target, args);
        System.out.println("需求匹配，帮忙找到一份A公司的工作");
        return result;
    }

    public static void main(String[] args) {
        Worker graduate = new Graduate();
        graduate = Headhunter2.getHelpedWorker(graduate);
        graduate.findJob();
    }
}
