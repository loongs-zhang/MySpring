package com.example.design.proxy.mydynamicproxy.test;

import com.example.design.proxy.mydynamicproxy.jdk.InvocationHandler;
import com.example.design.proxy.mydynamicproxy.jdk.Proxy;
import com.example.design.proxy.staticproxy.Graduate;
import com.example.design.proxy.staticproxy.Worker;

import java.lang.reflect.Method;

/**
 * @author SuccessZhang
 * @date 2020/05/13
 */
public class Headhunter4 implements InvocationHandler {

    private final Object target;

    public Headhunter4(Object target) {
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getHelpedWorker(final Object target) {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), new Headhunter4(target));
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
        graduate = Headhunter4.getHelpedWorker(graduate);
        graduate.findJob();
    }
}
