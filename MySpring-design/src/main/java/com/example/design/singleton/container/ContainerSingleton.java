package com.example.design.singleton.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.StampedLock;

/**
 * @author SuccessZhang
 * @date 2020/05/10
 */
public class ContainerSingleton {

    private static final StampedLock LOCK = new StampedLock();

    private static final Map<String, Object> CONTAINER = new ConcurrentHashMap<>();

    public static Object getInstance(Class<?> type) throws IllegalAccessException, InstantiationException {
        long stamp = LOCK.tryOptimisticRead();
        //通过类获取全量名
        String name = type.getName();
        if (!LOCK.validate(stamp)) {
            //版本失效
            try {
                stamp = LOCK.readLock();
                return CONTAINER.get(name);
            } finally {
                LOCK.unlockRead(stamp);
            }
        }
        try {
            //尝试将读锁升级为写锁
            long temp = LOCK.tryConvertToWriteLock(stamp);
            if (temp == 0) {
                //升级写锁失败
                stamp = LOCK.writeLock();
            } else {
                //升级写锁成功
                stamp = temp;
            }
            if (CONTAINER.get(name) == null) {
                System.out.println("newInstance");
                CONTAINER.put(name, type.newInstance());
            }
        } finally {
            LOCK.unlock(stamp);
        }
        return CONTAINER.get(name);
    }
}