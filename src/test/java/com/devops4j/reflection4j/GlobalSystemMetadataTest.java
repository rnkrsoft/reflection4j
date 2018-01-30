package com.devops4j.reflection4j;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by devops4j on 2017/12/3.
 */
public class GlobalSystemMetadataTest {

    @Test
    public void testForObject() throws Exception {
        MetaObject metaObject = GlobalSystemMetadata.forObject(BigDecimal.class, BigDecimal.ONE);
        System.out.println(metaObject.getGetterNames());
        System.out.println(metaObject.getSetterNames());
    }

    @Test
    public void testForClass() throws Exception {
        MetaClass metaClass = GlobalSystemMetadata.forClass(BigDecimal.class);
        System.out.println(metaClass.getGetterNames());
        System.out.println(metaClass.getSetterNames());
    }

    @Test
    public void testCreate() throws Exception {
        try {
            Object x = GlobalSystemMetadata.create("decimal");
            System.out.println(x);
            Assert.fail();
        }catch (Exception e){
        }
        try {
            BigDecimal x = GlobalSystemMetadata.create("decimal", new Class[]{String.class}, new String[]{"12.321"});
            Assert.assertEquals("12.321", x.toString());
        }catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testCreate1() throws Exception {
        BigDecimal[] b = GlobalSystemMetadata.create("bigdecimal[]");
        System.out.println(b);

        List list = GlobalSystemMetadata.create("arraylist");
        System.out.println(list);
        byte[] bytes = GlobalSystemMetadata.create("_byte[]");
        System.out.println(Arrays.toString(bytes));

    }

    @Test
    public void testCreate2() throws Exception {
        
    }
}