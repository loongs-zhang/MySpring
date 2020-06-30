package com.dragon.springframework.beans.factory;

import com.dragon.springframework.context.context.annotation.Bean;

/**
 * 由需要在BeanFactory设置完所有属性后作出反应的Bean实现的接口，
 * 例如执行自定义初始化，或仅检查是否已设置所有必填属性。
 * 实现InitializingBean的另一种方法是指定自定义初始化方法，
 * 例如使用{@link Bean#initMethod()}。
 *
 * @author SuccessZhang
 * @date 2020/06/07
 */
public interface InitializingBean {

    /**
     * 在注入了该bean所有属性后调用。
     */
    void afterPropertiesSet() throws Exception;

}