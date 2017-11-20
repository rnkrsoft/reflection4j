package com.devops4j.reflection.property;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by devops4j on 2017/7/13.
 */
public class PropertyNamerTest {

    @Test
    public void testMethodToProperty() throws Exception {
        Assert.assertEquals("name", PropertyNamer.methodToProperty("getName"));
        Assert.assertEquals("ok", PropertyNamer.methodToProperty("isOk"));
    }

    @Test
    public void testIsProperty() throws Exception {
        Assert.assertEquals(true, PropertyNamer.isProperty("getName"));
        Assert.assertEquals(false, PropertyNamer.isProperty("ge1tName"));
        Assert.assertEquals(true, PropertyNamer.isProperty("isOk"));
        Assert.assertEquals(false, PropertyNamer.isProperty("iasOk"));
    }

    @Test
    public void testIsGetter() throws Exception {
        Assert.assertEquals(true, PropertyNamer.isGetter("getName"));
        Assert.assertEquals(false, PropertyNamer.isGetter("ge1tName"));
        Assert.assertEquals(true, PropertyNamer.isGetter("isOk"));
        Assert.assertEquals(false, PropertyNamer.isGetter("iasOk"));
    }

    @Test
    public void testIsSetter() throws Exception {
        Assert.assertEquals(true, PropertyNamer.isSetter("setName"));
        Assert.assertEquals(false, PropertyNamer.isSetter("se1tName"));
    }
}