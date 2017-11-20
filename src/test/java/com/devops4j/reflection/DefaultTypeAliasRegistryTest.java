package com.devops4j.reflection;

import com.devops4j.reflection.factory.TypeAliasRegistryFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by devops4j on 2017/7/12.
 */
public class DefaultTypeAliasRegistryTest {

    @Test
    public void testGetInstance() throws Exception {
        TypeAliasRegistry typeAliasRegistry = TypeAliasRegistryFactory.getInstance();
        Assert.assertNotNull(typeAliasRegistry);
    }

    @Test
    public void testResolveAlias() throws Exception {
        TypeAliasRegistry typeAliasRegistry = TypeAliasRegistryFactory.getInstance();
        Assert.assertEquals(Integer.class, typeAliasRegistry.resolveAlias("int"));
        Assert.assertEquals(Integer.class, typeAliasRegistry.resolveAlias("integer"));
        Assert.assertEquals(Integer.TYPE, typeAliasRegistry.resolveAlias("_int"));

        Assert.assertEquals(Byte.class, typeAliasRegistry.resolveAlias("byte"));
        Assert.assertEquals(Byte.TYPE, typeAliasRegistry.resolveAlias("_byte"));

        Assert.assertEquals(Long.class, typeAliasRegistry.resolveAlias("long"));
        Assert.assertEquals(Long.TYPE, typeAliasRegistry.resolveAlias("_long"));

        Assert.assertEquals(Short.class, typeAliasRegistry.resolveAlias("short"));
        Assert.assertEquals(Short.TYPE, typeAliasRegistry.resolveAlias("_short"));

        Assert.assertEquals(Double.class, typeAliasRegistry.resolveAlias("double"));
        Assert.assertEquals(Double.TYPE, typeAliasRegistry.resolveAlias("_double"));

        Assert.assertEquals(Float.class, typeAliasRegistry.resolveAlias("float"));
        Assert.assertEquals(Float.TYPE, typeAliasRegistry.resolveAlias("_float"));

        Assert.assertEquals(Boolean.class, typeAliasRegistry.resolveAlias("boolean"));
        Assert.assertEquals(Boolean.TYPE, typeAliasRegistry.resolveAlias("_boolean"));
    }
}