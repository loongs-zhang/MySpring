package com.dragon.springframework.aop.aspectj.annotation;

import com.dragon.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Before advice
 *
 * @author <a href="mailto:alex AT gnilux DOT com">Alexandre Vasseur</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Before {
    /**
     * The pointcut expression
     * We allow "" as default for abstract pointcut
     */
    @AliasFor("expression")
    String value() default "";

    @AliasFor("value")
    String expression() default "";
}
