package com.devops4j.reflection4j.annotation;

import java.lang.annotation.*;

/**
 * Created by devops4j on 2017/11/20.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {
    String value();
}
