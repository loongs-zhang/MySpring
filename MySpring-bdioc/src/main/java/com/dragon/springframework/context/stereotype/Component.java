package com.dragon.springframework.context.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指示带注解的类是“组件”，当使用基于注解的配置和
 * 类路径扫描时，此类将被视为自动检测的候选类。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
@SuppressWarnings("unused")
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

    /**
     * 该值表示可能应用在逻辑组件的名称。
     */
    String value() default "";

}