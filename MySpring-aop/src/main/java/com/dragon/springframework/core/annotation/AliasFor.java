package com.dragon.springframework.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 一个用于为注解属性声明别名的注解。
 *
 * @author SuccessZhang
 * @date 2020/06/11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Repeatable(AliasFors.class)
public @interface AliasFor {

    /**
     * attribute属性的别名。
     */
    @AliasFor("attribute")
    String value() default "";

    /**
     * value属性的别名。
     */
    @AliasFor("value")
    String attribute() default "";

    /**
     * 别名的注解类型，默认为{@link Annotation}，
     * 表示别名属性是在与原属性相同的注解中声明的。
     */
    Class<? extends Annotation> annotation() default Annotation.class;

}
