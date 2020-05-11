package com.example.design.singleton.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.StampedLock;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
@SuppressWarnings("unused")
public class ContainerSingleton {

    private final StampedLock lock = new StampedLock();

    private static final Map<String, Object> CONTAINER = new ConcurrentHashMap<>();

    public Object getInstance(Class<?> type) throws IllegalAccessException, InstantiationException {
        long stamp = lock.readLock();
        try {
            //通过类获取类名
            String name = type.getName();
            Object instance = CONTAINER.get(name);
            if (instance == null) {
                //尝试将读锁升级为写锁
                long temp = lock.tryConvertToWriteLock(stamp);
                if (temp == 0) {
                    //升级写锁失败
                    lock.unlockRead(stamp);
                    stamp = lock.writeLock();
                } else {
                    //升级写锁成功
                    stamp = temp;
                }
                instance = type.newInstance();
                CONTAINER.put(name, instance);
            }
            return instance;
        } finally {
            lock.unlock(stamp);
        }
    }
}
