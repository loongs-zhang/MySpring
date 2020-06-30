package com.dragon.springframework.context;

import com.dragon.springframework.beans.factory.Aware;

/**
 * 希望通过其运行在其中的{@link ApplicationContext}
 * 通知任何对象的对象将实现的接口。
 *
 * @author SuccessZhang
 * @date 2020/06/12
 */
public interface ApplicationContextAware extends Aware {

    /**
     * 设置此对象在其中运行的ApplicationContext。
     */
    void setApplicationContext(ApplicationContext applicationContext) throws Exception;

}