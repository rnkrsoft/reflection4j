package com.devops4j.reflection4j.factory;

import com.devops4j.reflection4j.TypeAliasRegistry;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by devops4j on 2017/11/20.
 */
public class TypeAliasRegistryFactoryTest {

    @Test
    public void testGetInstance() throws Exception {
        TypeAliasRegistry registry = TypeAliasRegistryFactory.getInstance();
        Assert.assertNotNull(registry);
    }
}