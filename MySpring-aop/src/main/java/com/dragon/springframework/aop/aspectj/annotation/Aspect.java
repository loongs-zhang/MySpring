package com.dragon.springframework.aop.aspectj.annotation;

import com.dragon.springframework.context.stereotype.Component;
import com.dragon.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 切面声明。
 *
 * @author SuccessZhang
 * @date 2020/07/04
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Aspect {

    @AliasFor(annotation = Component.class)
    String value() default "";

}