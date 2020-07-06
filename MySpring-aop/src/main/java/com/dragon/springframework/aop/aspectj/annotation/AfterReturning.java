package com.dragon.springframework.aop.aspectj.annotation;

import com.dragon.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 正常返回通知。
 *
 * @author SuccessZhang
 * @date 2020/07/04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AfterReturning {

    /**
     * 绑定通知的切入点表达式。
     */
    @AliasFor("expression")
    String value() default "";

    @AliasFor("value")
    String expression() default "";

    /**
     * 将返回值绑定到参数的名称。
     */
    String returning() default "";

}