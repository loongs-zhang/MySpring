package com.example.demo.annotation.nature;

import java.lang.annotation.Annotation;

/**
 * @author SuccessZhang
 * @date 2020/06/14
 */
@SuppressWarnings("all")
public class ParentAnnotationImpl implements ParentAnnotation {
    @Override
    public String hello() {
        return "hello";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
