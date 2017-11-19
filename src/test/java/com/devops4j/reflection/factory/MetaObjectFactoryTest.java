package com.devops4j.reflection.factory;

import com.devops4j.reflection.MetaObject;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by devops4j on 2017/11/19.
 */
public class MetaObjectFactoryTest {
    @Data
    static class DemoBean{
        String name;
        int age;
    }
    @Test
    public void testForObject() throws Exception {
        MetaObject metaObject = MetaObjectFactory.forObject(new DemoBean());
        String[] getters = metaObject.getGetterNames();
        String[] setters = metaObject.getSetterNames();
        System.out.println(Arrays.toString(getters));
        System.out.println(Arrays.toString(setters));
        Assert.assertEquals(true, metaObject.hasGetter("name"));
        Assert.assertEquals(true, metaObject.hasGetter("age"));
        Assert.assertEquals(false, metaObject.hasGetter("age1"));
    }
}