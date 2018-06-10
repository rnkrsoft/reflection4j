package com.rnkrsoft.reflection4j.factory;

import com.rnkrsoft.reflection4j.TypeAliasRegistry;
import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Created by rnkrsoft on 2017/7/13.
 */
public class TypeAliasRegistryFactoryTest extends TestCase {

    public void testGetInstance() throws Exception {
        TypeAliasRegistry registry = TypeAliasRegistryFactory.getInstance();
        registry.registerAlias("i", Integer.class.getName());
        Class clazz = registry.resolveAlias("i");
        Assert.assertEquals(Integer.class, clazz);
        System.out.println(registry.getTypeAliases());
    }
}