package com.dragon.springframework.aop.aspectj.annotation;

import com.dragon.springframework.context.stereotype.Component;
import com.dragon.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Aspect declaration
 *
 * @author <a href="mailto:alex AT gnilux DOT com">Alexandre Vasseur</a>
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Aspect {

    /**
     * Per clause expression, defaults to singleton aspect
     * <p/>
     * Valid values are "" (singleton), "perthis(...)", etc
     */
    @AliasFor(annotation = Component.class)
    String value() default "";
}