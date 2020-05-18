package com.example.design.singleton.compareandset;

import sun.misc.Unsafe;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SuccessZhang
 * @date 2020/05/18
 */
public class CompareAndSetSingleton implements Serializable {

    private static volatile AtomicInteger count = new AtomicInteger(1);

    private static volatile CompareAndSetSingleton SINGLETON = null;

    private static Unsafe unsafe;

    private static long stateOffset;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            field = CompareAndSetSingleton.class.getDeclaredField("SINGLETON");
            stateOffset = unsafe.staticFieldOffset(field);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CompareAndSetSingleton() {
        System.out.println("init " + count.getAndIncrement() + " times");
        if (null != SINGLETON) {
            throw new RuntimeException("SINGLETON already exist!");
        }
    }

    private Object readResolve() {
        return SINGLETON;
    }

    public static CompareAndSetSingleton getInstance() {
        while (null == SINGLETON) {
            CompareAndSetSingleton target = new CompareAndSetSingleton();
            unsafe.compareAndSwapObject(CompareAndSetSingleton.class, stateOffset, null, target);
        }
        return SINGLETON;
    }
}
