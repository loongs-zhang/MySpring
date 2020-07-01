package com.dragon.springframework.beans.factory.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将一个构造函数、字段、setter方法或config方法标记为
 * 由Spring的依赖项注入工具自动装配可装配对象。
 *
 * @author SuccessZhang
 * @date 2020/06/22
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {

    /**
     * 需要自动装配的Bean名称。
     */
    String value() default "";

}
