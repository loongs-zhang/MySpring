package com.example.design.proxy.mydynamicproxy.test;

import com.example.design.proxy.mydynamicproxy.cglib.Enhancer;
import com.example.design.proxy.mydynamicproxy.jdk.Proxy;

/**
 * @author SuccessZhang
 * @date 2020/05/24
 */
public class InnerClassTest {

    public interface IService {
        Object test();

        /**
         * 测试重载的方法
         */
        Object test(int i, String j);
    }

    public static class InnerClass implements IService {
        @Override
        public Object test() {
            return 2;
        }

        @Override
        public Object test(int i, String j) {
            System.out.println("the i is [" + i + "] and the j is [" + j + "]");
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("cglib-------------------------------------------");
        InnerClass proxy1 = (InnerClass) Enhancer.create(InnerClass.class, new Headhunter5());
        if (proxy1 != null) {
            System.out.println(proxy1.test());
            System.out.println(proxy1.test(1, "cglib"));
        }
        System.out.println("jdk-------------------------------------------");
        IService target = new InnerClass();
        IService proxy2 = (IService) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), new Headhunter4(target));
        if (proxy2 != null) {
            System.out.println(proxy2.test());
            System.out.println(proxy2.test(1, "cglib"));
        }
    }
}
