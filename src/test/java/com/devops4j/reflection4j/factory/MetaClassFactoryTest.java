package com.devops4j.reflection4j.factory;

import com.devops4j.reflection4j.Invoker;
import com.devops4j.reflection4j.MetaClass;
import com.devops4j.reflection4j.GlobalSystemMetadata;
import lombok.Data;
import org.junit.Test;

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
        MetaClass metaClass = GlobalSystemMetadata.META_CLASS_FACTORY.forClass(DemoBean.class);
        DemoBean demoBean = new DemoBean();
        Invoker invoker1 = metaClass.getSetter("name");
        invoker1.invoke(demoBean, "xxxxx");
        Invoker invoker2 = metaClass.getGetter("name");
        System.out.println(invoker2.invoke(demoBean));
    }
}