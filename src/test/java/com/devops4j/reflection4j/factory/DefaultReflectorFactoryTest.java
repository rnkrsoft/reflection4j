package com.devops4j.reflection4j.factory;

import com.devops4j.reflection4j.Invoker;
import com.devops4j.reflection4j.Reflector;
import com.devops4j.reflection4j.ReflectorFactory;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by devops4j on 2017/11/19.
 */
public class DefaultReflectorFactoryTest {
    @Data
    static class DemoBean {
        String name;
        int age;
    }

    @Test
    public void testIsClassCacheEnabled() throws Exception {

    }

    @Test
    public void testSetClassCacheEnabled() throws Exception {

    }

    @Test
    public void testFindForClass() throws Throwable{
        ReflectorFactory factory = new DefaultReflectorFactory();
        Reflector reflector = factory.findForClass(DemoBean.class);
        DemoBean demoBean = new DemoBean();
        demoBean.setName("xxx");
        demoBean.setAge(23);
        {
            Invoker invoker = reflector.getGetter("name");
            String name = invoker.invoke(demoBean);
            Assert.assertEquals("xxx", name);
        }
        {
            Invoker invoker = reflector.getGetter("age");
            int age = invoker.invoke(demoBean);
            Assert.assertEquals(23, age);
        }
    }
}