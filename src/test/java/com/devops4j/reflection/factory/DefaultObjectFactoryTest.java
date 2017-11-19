package com.devops4j.reflection.factory;

import com.devops4j.reflection.ObjectFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by devops4j on 2017/11/19.
 */
public class DefaultObjectFactoryTest {
    static class DemoBean{

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
    }
}