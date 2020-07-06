package com.dragon.springframework.core.annotation;

import com.dragon.springframework.core.Ordered;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义带注解的组件的排序顺序。{@link #value}是可选的，
 * 它表示在界面中定义的排序值。较低的值具有较高的优先级，
 * 默认为{@code Ordered.LOWEST_PRECEDENCE}，表示最低优先级。
 *
 * @author SuccessZhang
 * @date 2020/07/01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Documented
public @interface Order {

    /**
     * 排序值。
     */
    int value() default Ordered.LOWEST_PRECEDENCE;

}
