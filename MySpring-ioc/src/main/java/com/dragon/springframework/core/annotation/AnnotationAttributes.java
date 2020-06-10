package com.dragon.springframework.core.annotation;

import com.dragon.springframework.core.Assert;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;

class AnnotationAttributes extends LinkedHashMap<String, Object> {

    private final Class<? extends Annotation> annotationType;

    AnnotationAttributes(Class<? extends Annotation> annotationType) {
        Assert.notNull(annotationType, "'annotationType' must not be null");
        this.annotationType = annotationType;
    }

    /**
     * Get the type of annotation represented by this {@code AnnotationAttributes}.
     */
    Class<? extends Annotation> annotationType() {
        return this.annotationType;
    }

}
