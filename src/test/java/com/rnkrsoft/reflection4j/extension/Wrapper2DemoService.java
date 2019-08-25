package com.rnkrsoft.reflection4j.extension;

/**
 * Created by rnkrsoft.com on 2019/8/24.
 */
@Extension("x2")
@Wrapper(priority = 2)
public class Wrapper2DemoService implements DemoService{
    DemoService instance;
    public Wrapper2DemoService(DemoService instance){
        this.instance = instance;
    }

    @Override
    public String say() {
        System.out.println("Wrapper2DemoService before");
        String value = instance.say();
        System.out.println("Wrapper2DemoService return");
        return value;
    }
}
