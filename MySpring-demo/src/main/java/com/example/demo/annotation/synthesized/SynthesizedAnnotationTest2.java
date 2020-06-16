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
public class SynthesizedAnnotationTest2 {

    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Test1 {

        @AliasFor(annotation = Test1.class, attribute = "test12")
        String test1() default "test1";

        @AliasFor(annotation = Test1.class, attribute = "test1")
        String test12() default "test1";
    }

    @Test1(test1 = "覆盖Test1属性中的test1方法和test12方法")
    public static class Element2 {
    }

    public static void main(String[] args) {
        Test1 annotation2 = AnnotatedElementUtils.getMergedAnnotation(Element2.class, Test1.class);
        System.out.println("test1->" + annotation2.test1());
        System.out.println("test12->" + annotation2.test12());
    }
}
