package com.devops4j.reflection4j.factory;

import com.devops4j.reflection4j.GlobalSystemMetadata;
import com.devops4j.reflection4j.MetaObject;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by devops4j on 2017/11/19.
 */
public class MetaObjectFactoryTest {
    @Data
    static class DemoBean {
        String name;
        int age;
    }

    @Test
    public void testForObject() throws Exception {
        MetaObject metaObject = GlobalSystemMetadata.META_OBJECT_FACTORY.forObject(new DemoBean());
        Collection<String> getters = metaObject.getGetterNames();
        Collection<String> setters = metaObject.getSetterNames();
        System.out.println(getters);
        System.out.println(setters);
        Assert.assertEquals(true, metaObject.hasGetter("name"));
        Assert.assertEquals(true, metaObject.hasGetter("age"));
        Assert.assertEquals(false, metaObject.hasGetter("age1"));
        metaObject.setValue("name", "xxxxx");
        String name = metaObject.getValue("name");
        Assert.assertEquals("xxxxx", name);
    }
}