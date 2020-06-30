package com.dragon.springframework.context.context.annotation;

import com.dragon.springframework.context.stereotype.Component;
import com.dragon.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指示方法产生一个由Spring容器管理的bean（作者进行了魔改，
 * 使得@Bean变为直接在类上使用），此注解的属性的名称和语义
 * 是故意类似于Spring XML配置文件中的<bean/>元素的语义。
 *
 * @author SuccessZhang
 * @date 2020/06/09
 */
@Component
@Documented
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {

    /**
     * 此Bean的名称，如果未指定，则bean的名称为带注解的
     * 简单类名（首字母小写）。如果指定，简单类名将被忽略。
     * 如果未声明其他属性，也可以通过属性配置bean名称。
     */
    @AliasFor(annotation = Component.class, attribute = "value")
    String value() default "";

    /**
     * 是否通过Bean名称自动装配。
     */
    boolean autowire() default false;

    /**
     * 初始化期间在Bean实例上调用的初始化方法的名称。
     */
    String initMethod() default "";

    /**
     * 关闭应用程序上下文时要在Bean实例上调用的销毁方法的名称，但是
     * 仅在生命周期处于工厂的完全控制之下的bean上调用（也就是单例的
     * 那些bean），对于任何其他scope并不能保证。
     */
    String destroyMethod() default "";

}