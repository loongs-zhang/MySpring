package com.dragon.springframework.core.annotation.test.repeatable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 一个简单的注解。
 *
 * @author SuccessZhang
 * @date 2020/07/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Repeatable(SimpleAnnotations.class)
public @interface SimpleAnnotation {

    String value() default "";

}
