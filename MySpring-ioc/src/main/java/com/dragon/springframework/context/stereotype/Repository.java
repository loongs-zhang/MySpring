package com.dragon.springframework.context.stereotype;

import com.dragon.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指示带注解的类是“存储库”，主要用在DAO类上，
 * 从Spring 2.5开始，此注解还用作{@link Component}的特化，
 * 从而允许通过类路径扫描自动检测实现类。
 *
 * @author SuccessZhang
 * @date 2020/06/18
 */
@SuppressWarnings("unused")
@Component
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Repository {

    @AliasFor(annotation = Component.class)
    String value() default "";

}
