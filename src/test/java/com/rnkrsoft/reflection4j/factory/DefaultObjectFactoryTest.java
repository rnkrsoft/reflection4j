package com.rnkrsoft.reflection4j.factory;

import com.rnkrsoft.reflection4j.ObjectFactory;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by rnkrsoft on 2017/11/19.
 */
public class DefaultObjectFactoryTest {
    @Data
    static class DemoBean {
        String name;
        public DemoBean() {
        }

        public DemoBean(String name) {
            this.name = name;
        }
    }

    @Test
    public void testCreate() throws Exception {
        ObjectFactory factory = new DefaultObjectFactory();
        Assert.assertEquals(ArrayList.class, factory.create(List.class).getClass());
        Assert.assertEquals(ArrayList.class, factory.create(Collection.class).getClass());
        Assert.assertEquals(ArrayList.class, factory.create(Iterable.class).getClass());
        Assert.assertEquals(HashSet.class, factory.create(Set.class).getClass());
        Assert.assertEquals(HashMap.class, factory.create(Map.class).getClass());
        Assert.assertEquals(TreeSet.class, factory.create(SortedSet.class).getClass());
        Assert.assertEquals(DemoBean.class, factory.create(DemoBean.class).getClass());
        List types = Arrays.asList(String.class);
        List args = Arrays.asList("xxxxx");
        Assert.assertEquals(DemoBean.class, factory.create(DemoBean.class, types, args).getClass());

    }

    @Test
    public void testCreate2() throws Exception {
        ObjectFactory factory = new DefaultObjectFactory();
        System.out.println(factory.getMappings());

    }
}