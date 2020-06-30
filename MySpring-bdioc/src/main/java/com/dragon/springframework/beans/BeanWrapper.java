package com.dragon.springframework.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 提供用于分析和操作标准JavaBean的操作：获得和设置属性值（单独或批量），
 * 获取属性描述符，以及查询属性的可读性/可写性。在原生BeanWrapper是一个
 * 接口，在这里作者为了便于实现进行了简化（主要参考了BeanWrapperImpl）。
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
@Data
@NoArgsConstructor
public class BeanWrapper {

    /**
     * 原生对象
     */
    private Object rootObject;

    /**
     * 原生对象Class
     */
    private Class<?> rootClass;

    /**
     * 代理包装之后的对象
     */
    private Object wrappedObject;

    /**
     * 代理包装之后的对象Class
     */
    private Class<?> wrappedClass;

    public BeanWrapper(Object rootObject) {
        this.rootObject = rootObject;
        this.rootClass = rootObject.getClass();
    }

    public BeanWrapper(Object rootObject, Class<?> rootClass) {
        this.rootObject = rootObject;
        this.rootClass = rootClass;
    }
}
