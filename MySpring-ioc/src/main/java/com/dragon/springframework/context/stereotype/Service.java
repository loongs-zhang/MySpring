package com.dragon.springframework.context.stereotype;

import com.dragon.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指示带注解的类是“服务”，此注解用作{@link Component}的特化，
 * 允许通过类路径扫描自动检测实现类。
 *
 * @author SuccessZhang
 * @date 2020/06/08
 */
@SuppressWarnings("unused")
@Component
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

    @AliasFor(annotation = Component.class, attribute = "value")
    String value() default "";

}
