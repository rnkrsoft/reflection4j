package com.devops4j.reflection.factory;

import com.devops4j.reflection.Invoker;
import com.devops4j.reflection.Reflector;
import com.devops4j.reflection.ReflectorFactory;
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
            Invoker invoker = reflector.getGetInvoker("name");
            String name = invoker.invoke(demoBean);
            Assert.assertEquals("xxx", name);
        }
        {
            Invoker invoker = reflector.getGetInvoker("age");
            int age = invoker.invoke(demoBean);
            Assert.assertEquals(23, age);
        }
    }
}