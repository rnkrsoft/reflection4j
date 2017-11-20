package com.devops4j.reflection;


import com.devops4j.reflection.factory.DefaultReflectorFactory;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by devops4j on 2017/7/14.
 */
public class DefaultMetaClassTest {
    @Data
    static class DemoBean {
        String name;
    }

    @Test
    public void test1() throws Throwable {
        MetaClass metaClass = new DefaultMetaClass(DemoBean.class, new DefaultReflectorFactory());
        Invoker invoker = metaClass.getGetInvoker("name");
        DemoBean demoBean = new DemoBean();
        demoBean.setName("xxxx");
        String name = invoker.invoke(demoBean);
        System.out.println(name);
        Assert.assertEquals("xxxx", name);
    }
}