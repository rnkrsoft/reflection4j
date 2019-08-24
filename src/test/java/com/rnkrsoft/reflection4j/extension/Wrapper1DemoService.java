package com.rnkrsoft.reflection4j.extension;

/**
 * Created by rnkrsoft.com on 2019/8/24.
 */
@Wrapper(priority = 1)
public class Wrapper1DemoService implements DemoService{
    DemoService instance;
    public Wrapper1DemoService(DemoService instance){
        this.instance = instance;
    }

    @Override
    public String say() {
        System.out.println("Wrapper1DemoService before");
        String value = instance.say();
        System.out.println("Wrapper1DemoService return");
        return value;
    }
}
