package com.example.demo;

import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import java.lang.reflect.Method;

/**
 * cglib获取方法、调用和jdk的对比。
 *
 * @author SuccessZhang
 * @date 2020/7/17
 */
public class PerformanceTest {

    private static final int INT = 1;
    private static final String STRING = "name";
    private static final Object[] INTS = {1};
    private static final Object[] STRINGS = new String[]{STRING};

    private static final Bean BEAN = new Bean();

    private static final MethodBean METHOD = new MethodBean();
    private static final OptimizationMethodBean OPTIMIZATION_METHOD = new OptimizationMethodBean();
    private static final CglibMethod CGLIB_METHOD = new CglibMethod();

    private static final long LOOP = 10000 * 10000;

    // 测试main
    public static void main(String[] args) {
        // 通过getMethod获取方法
        reflectionGet();
        // 通过getDeclaredMethod获取方法
        reflectionDeclaredGet();
        // 通过FastClass获取FastMethod
        cglibReflectionGet();
        // 直接调用
        directInvoke();
        // 反射调用
        reflectionInvoke();
        // 优化后反射调用
        optimizationReflectionInvoke();
        // cglib反射调用
        cglibReflectionInvoke();
    }

    /**
     * 通过getMethod获取方法测试。
     */
    public static void reflectionGet() {
        long start = System.currentTimeMillis();
        for (long i = 0; i < LOOP; i++) {
            try {
                Bean.class.getMethod("setId", int.class);
                Bean.class.getMethod("setName", String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("通过getMethod获取方法测试:" + dur);
    }

    /**
     * 通过getDeclaredMethod获取方法测试。
     */
    public static void reflectionDeclaredGet() {
        long start = System.currentTimeMillis();
        for (long i = 0; i < LOOP; i++) {
            try {
                Bean.class.getDeclaredMethod("setId", int.class);
                Bean.class.getDeclaredMethod("setName", String.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("通过getDeclaredMethod获取方法测试:" + dur);
    }

    /**
     * 通过getDeclaredMethod获取方法测试。
     */
    public static void cglibReflectionGet() {
        long start = System.currentTimeMillis();
        try {
            FastClass fastClass = FastClass.create(Bean.class);
            Method setId = Bean.class.getDeclaredMethod("setId", int.class);
            Method setName = Bean.class.getDeclaredMethod("setName", String.class);
            for (long i = 0; i < LOOP; i++) {
                fastClass.getMethod(setId);
                fastClass.getMethod(setName);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("通过FastClass获取FastMethod测试:" + dur);
    }

    /**
     * 直接调用测试
     */
    public static void directInvoke() {
        long start = System.currentTimeMillis();
        for (long i = 0; i < LOOP; i++) {
            BEAN.setId(INT);
            BEAN.setName(STRING);
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("直接调用测试:" + dur);
    }

    /**
     * 反射调用测试
     */
    public static void reflectionInvoke() {
        long start = System.currentTimeMillis();
        for (long i = 0; i < LOOP; i++) {
            try {
                METHOD.setId.invoke(BEAN, INTS);
                METHOD.setName.invoke(BEAN, STRINGS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("反射调用测试:" + dur);
    }

    /**
     * 优化后反射调用测试
     */
    public static void optimizationReflectionInvoke() {
        long start = System.currentTimeMillis();
        for (long i = 0; i < LOOP; i++) {
            try {
                OPTIMIZATION_METHOD.setId.invoke(BEAN, INTS);
                OPTIMIZATION_METHOD.setName.invoke(BEAN, STRINGS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("优化后反射调用测试:" + dur);
    }

    /**
     * cglib反射调用测试
     */
    public static void cglibReflectionInvoke() {
        long start = System.currentTimeMillis();
        for (long i = 0; i < LOOP; i++) {
            try {
                CGLIB_METHOD.cglibSetId.invoke(BEAN, INTS);
                CGLIB_METHOD.cglibSetName.invoke(BEAN, STRINGS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long dur = System.currentTimeMillis() - start;
        System.out.println("cglib反射调用测试:" + dur);
    }

    /**
     * 测试的bean, 简单的int String类型
     */
    @SuppressWarnings("unused")
    public static class Bean {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 反射测试需要:Method bean
     */
    public static class MethodBean {

        public Method setId;
        public Method setName;

        {
            try {
                setId = Bean.class.getDeclaredMethod("setId", int.class);
                setName = Bean.class.getDeclaredMethod("setName", String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 反射测试需要:优化后的Method bean
     */
    public static class OptimizationMethodBean extends MethodBean {
        {
            /* 所谓的优化 */
            setId.setAccessible(true);
            setName.setAccessible(true);
        }
    }

    /**
     * 反射测试需要,使用cglib的fast method
     */
    public static class CglibMethod extends MethodBean {
        public FastMethod cglibSetId;
        public FastMethod cglibSetName;
        private final FastClass cglibBeanClass = FastClass.create(Bean.class);

        {
            cglibSetId = cglibBeanClass.getMethod(setId);
            cglibSetName = cglibBeanClass.getMethod(setName);
        }
    }

}