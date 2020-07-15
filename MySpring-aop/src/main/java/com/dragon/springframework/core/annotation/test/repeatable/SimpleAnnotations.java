package com.dragon.springframework.core.annotation.test.repeatable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 简单注解的数组。
 *
 * @author SuccessZhang
 * @date 2020/07/13
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SimpleAnnotations {

    SimpleAnnotation[] value() default {};

}
