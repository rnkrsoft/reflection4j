package com.devops4j.reflection.wrapper;

import com.devops4j.reflection.property.PropertyTokenizer;
import org.junit.Test;
import com.devops4j.reflection.property.DemoBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by devops4j on 2017/7/14.
 */
public class CollectionWrapperTest {

    @Test(expected = UnsupportedOperationException.class)
    public void testGet() throws Exception {
        PropertyTokenizer propertyTokenizer = new PropertyTokenizer("x[1].fieldName");
        List list =  new ArrayList();
        list.add(new DemoBean("test1", 12, BigDecimal.TEN));
        CollectionWrapper collectionWrapper = new CollectionWrapper(null,list);
        Object val = collectionWrapper.get(propertyTokenizer);
    }

    @Test
    public void testSet() throws Exception {

    }

    @Test
    public void testFindProperty() throws Exception {

    }

    @Test
    public void testGetGetterNames() throws Exception {

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
    public void testAdd() throws Exception {
        List list =  new ArrayList();
        list.add(new DemoBean("test1", 12, BigDecimal.TEN));
        CollectionWrapper collectionWrapper = new CollectionWrapper(null,list);
        collectionWrapper.add(new DemoBean("test2", 13, BigDecimal.TEN));
        Object obj = collectionWrapper.getNativeObject();
        System.out.println(obj);
    }

    @Test
    public void testAddAll() throws Exception {
        List list =  new ArrayList();
        list.add(new DemoBean("test1", 12, BigDecimal.TEN));
        CollectionWrapper collectionWrapper = new CollectionWrapper(null,list);
        collectionWrapper.addAll(list);
        Object obj = collectionWrapper.getNativeObject();
        System.out.println(obj);
    }
}