package com.rnkrsoft.reflection4j.annotation;

import java.lang.annotation.*;

/**
 * Created by rnkrsoft on 2017/11/20.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {
    String value();
}
