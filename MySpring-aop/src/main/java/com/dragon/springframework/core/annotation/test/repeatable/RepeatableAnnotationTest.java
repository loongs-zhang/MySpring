package com.dragon.springframework.core.annotation.test.repeatable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author SuccessZhang
 * @date 2020/07/13
 */
public class RepeatableAnnotationTest {

    @SimpleAnnotation("0")
    public void test() {
        System.out.println("test");
    }

    @SimpleAnnotation("1")
    @SimpleAnnotation("2")
    public void test2() {
        System.out.println("test2");
    }

    public static void main(String[] args) {
        try {
            Method test = getMethod("test");
            Annotation[] annotations = test.getAnnotations();
            System.out.println(Arrays.toString(annotations));
            SimpleAnnotations simpleAnnotations = (SimpleAnnotations) annotations[0];
            System.out.println(Arrays.toString(simpleAnnotations.value()));
        } catch (Exception ignored) {
        }
        try {
            Method test = getMethod("test2");
            Annotation[] annotations = test.getAnnotations();
            System.out.println(Arrays.toString(annotations));
            SimpleAnnotations simpleAnnotations = (SimpleAnnotations) annotations[0];
            System.out.println(Arrays.toString(simpleAnnotations.value()));
        } catch (Exception ignored) {
        }

    }

    public static Method getMethod(String name) throws NoSuchMethodException {
        return RepeatableAnnotationTest.class.getDeclaredMethod(name);
    }
}
