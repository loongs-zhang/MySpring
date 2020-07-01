package com.dragon.springframework.context.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指示是否要延迟初始化bean。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lazy {

    /**
     * 是否延时加载，默认不延时加载。
     */
    boolean value() default false;

}