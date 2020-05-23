package com.example.design.template.after;

import com.example.design.template.before.Transaction;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author SuccessZhang
 * @date 2020/05/23
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class AbstractDao<T> implements MethodInterceptor {

    private T proxy;

    public T getProxy() {
        if (proxy == null) {
            Type type = getClass().getGenericSuperclass();
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Class<T> proxyClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
            proxy = (T) Enhancer.create(proxyClass, this);
        }
        return proxy;
    }

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        try {
            Transaction transaction = (Transaction) args[0];
            try {
                proxy.invokeSuper(object, args);
                transaction.commit();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            System.out.println("method:[" + method.toString() + "],its first arg should be the type of " + Transaction.class.getName());
            return false;
        }
    }
}
