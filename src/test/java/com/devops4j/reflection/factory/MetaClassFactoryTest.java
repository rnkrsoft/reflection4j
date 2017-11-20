package com.devops4j.reflection.factory;

import com.devops4j.reflection.Invoker;
import com.devops4j.reflection.MetaClass;
import lombok.Data;
import lombok.Setter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by devops4j on 2017/11/20.
 */
public class MetaClassFactoryTest {
    @Data
    static class DemoBean{
        String name;
    }

    @Test
    public void testForClass() throws Throwable{
        MetaClass metaClass = MetaClassFactory.forClass(DemoBean.class);
        DemoBean demoBean = new DemoBean();
        Invoker invoker1 = metaClass.getSetInvoker("name");
        invoker1.invoke(demoBean, "xxxxx");
        Invoker invoker2 = metaClass.getGetInvoker("name");
        System.out.println(invoker2.invoke(demoBean));
    }
}