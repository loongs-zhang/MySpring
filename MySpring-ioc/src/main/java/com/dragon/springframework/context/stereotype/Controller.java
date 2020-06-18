package com.dragon.springframework.context.stereotype;

import com.dragon.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示带注解的类是“控制器”（例如Web控制器），此注解用作
 * {@link Component}的特化，允许通过类路径扫描自动检测实现类，
 * 通常与{@code @RequestMapping}结合使用。
 *
 * @author SuccessZhang
 * @date 2020/06/18
 */
@SuppressWarnings("unused")
@Component
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

    @AliasFor(annotation = Component.class)
    String value() default "";

}
