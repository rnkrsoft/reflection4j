package com.rnkrsoft.reflection4j.extension;

/**
 * Created by rnkrsoft.com on 2019/8/23.
 */
@Extension("en")
public class EnDemoService implements DemoService{
    @Override
    public String say() {
        return "Hello!";
    }
}
