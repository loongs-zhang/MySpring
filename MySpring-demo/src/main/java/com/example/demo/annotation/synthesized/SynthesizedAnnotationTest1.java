package com.example.demo.annotation.synthesized;

import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author SuccessZhang
 * @date 2020/06/15
 */
public class SynthesizedAnnotationTest1 {

    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Test1 {
        String test1() default "test1";
    }

    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Test1
    public @interface Test2 {
        @AliasFor(annotation = Test1.class, attribute = "test1")
        String test2() default "test2";
    }

    @Test2(test2 = "覆盖Test1属性中的test1方法")
    public static class Element {
    }

    public static void main(String[] args) {
        Test1 annotation = AnnotatedElementUtils.getMergedAnnotation(Element.class, Test1.class);
        System.out.println(annotation.hashCode());
        System.out.println(annotation.toString());
        System.out.println(annotation.annotationType());
    }
}
