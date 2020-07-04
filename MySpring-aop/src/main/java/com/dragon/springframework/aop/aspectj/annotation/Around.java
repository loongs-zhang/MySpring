package com.dragon.springframework.aop.aspectj.annotation;


import com.dragon.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Around advice
 *
 * @author SuccessZhang
 * @date 2020/07/04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Around {

    /**
     * The pointcut expression where to bind the advice
     */
    @AliasFor("expression")
    String value() default "";

    @AliasFor("value")
    String expression() default "";

}