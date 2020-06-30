package com.dragon.springframework.core.proxy.test;

import com.dragon.springframework.core.proxy.cglib.Enhancer;
import com.dragon.springframework.core.proxy.jdk.Proxy;

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

    public static class InnerClassImpl implements IService {
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
        InnerClassImpl proxy1 = (InnerClassImpl) Enhancer.create(InnerClassImpl.class, new EnhancerTest());
        if (proxy1 != null) {
            System.out.println(proxy1.test());
            System.out.println(proxy1.test(1, "cglib"));
        }
        System.out.println("jdk-------------------------------------------");
        IService target = new InnerClassImpl();
        IService proxy2 = (IService) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), new ProxyTest(target));
        if (proxy2 != null) {
            System.out.println(proxy2.test());
            System.out.println(proxy2.test(1, "cglib"));
        }
    }
}
