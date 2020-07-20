package com.dragon.springframework.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * 负责从基础源提取属性值的属性提取器。
 *
 * @author SuccessZhang
 * @date 2020/06/11
 */
interface AnnotationAttributeExtractor {

    /**
     * 获取被提取属性的注解的类型。
     */
    Class<? extends Annotation> getAnnotationType();

    /**
     * 获取被注解标注的元素。
     */
    AnnotatedElement getAnnotatedElement();

    /**
     * 获取属性值。
     */
    Object getAttributeValue(String attribute);

}
