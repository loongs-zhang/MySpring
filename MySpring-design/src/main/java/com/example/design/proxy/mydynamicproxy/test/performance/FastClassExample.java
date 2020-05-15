package com.example.design.proxy.mydynamicproxy.test.performance;

import java.lang.reflect.Method;

/**
 * @author SuccessZhang
 * @date 2020/05/14
 */
@SuppressWarnings("unused")
public class FastClassExample {

    private static class Graduate {
        public void findJob() {
            System.out.println("findJob");
        }
    }

    public FastClassExample() {
    }

    public int getIndex(String name, Class[] parameterTypes) {
        StringBuilder sb = new StringBuilder(name);
        for (Class parameterType : parameterTypes) {
            sb.append(parameterType.getName());
        }
        switch (sb.toString().hashCode()) {
            case -853203132:
                return 0;
            default:
                break;
        }
        return -1;
    }

    public Object invoke(int index, Object obj, Object[] args) {
        Graduate object = (Graduate) obj;
        switch (index) {
            case 0:
                object.findJob();
                break;
            default:
                break;
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        //反射调用方式
        Graduate graduate = new Graduate();
        Method method = Graduate.class.getDeclaredMethod("findJob");
        method.invoke(graduate);
        //FastClass调用方式
        FastClassExample fastClass = new FastClassExample();
        int index = fastClass.getIndex(method.getName(), method.getParameterTypes());
        fastClass.invoke(index, graduate, new Object[]{});
    }
}