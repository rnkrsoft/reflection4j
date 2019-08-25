package com.rnkrsoft.reflection4j.extension;

import java.lang.annotation.*;

/**
 * Created by rnkrsoft.com on 2019/8/24.
 * 用于定义拓展点实现类上，用于定义拓展点名称
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Extension {
    /**
     * 拓展点名称
     *
     * @return
     */
    String value();
}
