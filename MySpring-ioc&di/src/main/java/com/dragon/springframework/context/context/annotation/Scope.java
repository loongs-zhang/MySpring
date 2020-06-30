package com.dragon.springframework.context.context.annotation;

import com.dragon.springframework.beans.config.ConfigurableBeanFactory;
import com.dragon.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示要用于带Component注解的实例的作用域名称。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    @AliasFor("scopeName")
    String value() default ConfigurableBeanFactory.SCOPE_SINGLETON;

    /**
     * 指定用于带注解的Component/bean的作用域名称。
     */
    @AliasFor("value")
    String scopeName() default ConfigurableBeanFactory.SCOPE_SINGLETON;

}
