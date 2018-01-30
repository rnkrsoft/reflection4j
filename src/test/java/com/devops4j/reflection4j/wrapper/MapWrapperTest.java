package com.devops4j.reflection4j.wrapper;

import com.devops4j.reflection4j.GlobalSystemMetadata;
import com.devops4j.reflection4j.property.PropertyTokenizer;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by devops4j on 2017/7/14.
 */
public class MapWrapperTest {

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testSet() throws Exception {

    }

    @Test
    public void testFindProperty() throws Exception {

    }

    @Test
    public void testGetGetterNames() throws Exception {
        Map map = new HashMap();
        map.put("key1", "val1");
        map.put("key2", "val2");
        MapWrapper mapWrapper = new MapWrapper( Map.class, GlobalSystemMetadata.META_OBJECT_FACTORY.forObject("",Map.class, map),map);
        PropertyTokenizer propertyTokenizer = new PropertyTokenizer( "key1");
        Object val1 = mapWrapper.get(propertyTokenizer);
        Assert.assertEquals("val1", val1);
    }

    @Test
    public void testGetSetterNames() throws Exception {

    }

    @Test
    public void testGetSetterType() throws Exception {

    }

    @Test
    public void testGetGetterType() throws Exception {

    }

    @Test
    public void testHasSetter() throws Exception {

    }

    @Test
    public void testHasGetter() throws Exception {

    }

    @Test
    public void testIsCollection() throws Exception {

    }

    @Test
    public void testAdd1() throws Exception {

    }

    @Test
    public void testAddAll1() throws Exception {

    }

    @Test
    public void testGetNativeObject() throws Exception {

    }
}