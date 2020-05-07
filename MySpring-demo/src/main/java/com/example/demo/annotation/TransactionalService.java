package com.example.demo.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SuccessZhang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
@Transactional
public @interface TransactionalService {
    @AliasFor(annotation = Service.class, attribute = "value")
    String value() default "";

    @AliasFor(annotation = Transactional.class, attribute = "value")
    String transactionManager() default "";
}
