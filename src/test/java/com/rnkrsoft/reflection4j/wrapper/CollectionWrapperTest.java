package com.rnkrsoft.reflection4j.wrapper;

import com.rnkrsoft.reflection4j.property.PropertyTokenizer;
import lombok.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnkrsoft on 2017/7/14.
 */
public class CollectionWrapperTest {


    @Data
    @ToString
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    static public class DemoBean {
        protected String name;
        protected int age;
        protected BigDecimal amt;
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGet() throws Exception {
        PropertyTokenizer propertyTokenizer = new PropertyTokenizer("x[1].fieldName");
        List list = new ArrayList();
        list.add(new DemoBean("test1", 12, BigDecimal.TEN));
        CollectionWrapper collectionWrapper = new CollectionWrapper(List.class, null, list);
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
        List list = new ArrayList();
        list.add(new DemoBean("test1", 12, BigDecimal.TEN));
        CollectionWrapper collectionWrapper = new CollectionWrapper(List.class, null, list);
        collectionWrapper.add(new DemoBean("test2", 13, BigDecimal.TEN));
        Object obj = collectionWrapper.getNativeObject();
        System.out.println(obj);
    }

    @Test
    public void testAddAll() throws Exception {
        List list = new ArrayList();
        list.add(new DemoBean("test1", 12, BigDecimal.TEN));
        CollectionWrapper collectionWrapper = new CollectionWrapper(List.class, null, list);
        collectionWrapper.addAll(list);
        Object obj = collectionWrapper.getNativeObject();
        System.out.println(obj);
    }
}