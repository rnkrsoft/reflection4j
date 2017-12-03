package com.devops4j.reflection4j.meta;

import com.devops4j.reflection4j.MetaClass;
import com.devops4j.reflection4j.MetaObject;
import com.devops4j.reflection4j.GlobalSystemMetadata;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by devops4j on 2017/12/3.
 */
public class DefaultMetaObjectTest {
    @Test
    public void testMetaObjectForProperty1() throws Exception {
        DemoBean demoBean = new DemoBean();
        demoBean.setCol("11111");
        MetaObject metaObject = new DefaultMetaObject(demoBean, GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        MetaObject metaObject1 = metaObject.metaObjectForProperty("bean1.bean2");
        Assert.assertEquals(GlobalSystemMetadata.NULL_META_OBJECT, metaObject1);
    }

    @Test
    public void testInstantiatePropertyValue() throws Exception {
        DemoBean demoBean = new DemoBean();
        demoBean.setCol("11111");
        MetaObject metaObject = new DefaultMetaObject(demoBean, GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        MetaObject metaObject1 = metaObject.instantiatePropertyValue("bean1.bean2");
        System.out.println(demoBean);
        MetaObject metaObject2 = metaObject.instantiatePropertyValue("bean1.bean2.col2");
        System.out.println(demoBean);
    }


    @Test
    public void testGetMetaClass() throws Exception {
        DemoBean demoBean = new DemoBean();
        demoBean.setCol("11111");
        MetaObject metaObject = new DefaultMetaObject(demoBean, GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        MetaClass metaClass1 = metaObject.metaClassForProperty("bean1");
        Assert.assertEquals(DemoBean1.class, metaClass1.getType());
        System.out.println(metaClass1.getGetterNames());
        MetaClass metaClass2 = metaObject.metaClassForProperty("bean1.bean2");
        Assert.assertEquals(DemoBean2.class, metaClass2.getType());
        System.out.println(metaClass2.getGetterNames());
        MetaClass metaClass3 = metaObject.metaClassForProperty("bean1.bean2.col2");
        Assert.assertEquals(String.class, metaClass3.getType());
        System.out.println(metaClass3.getGetterNames());
    }

    @Data
    static class DemoBean {
        String col;
        DemoBean1 bean1;
    }

    @Data
    static class DemoBean1 {
        String col1;
        DemoBean2 bean2;
    }

    @Data
    static class DemoBean2 {
        String col2;
    }

    @Test
    public void testHasGetter() throws Exception {
        DemoBean demoBean = new DemoBean();
        demoBean.setCol("11111");
        MetaObject metaObject = new DefaultMetaObject(demoBean, GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        Assert.assertEquals(2, metaObject.getSetterNames().size());
        Assert.assertEquals(true, metaObject.getSetterNames().contains("col"));
        Assert.assertEquals(true, metaObject.getGetterNames().contains("col"));
        Assert.assertEquals(true, metaObject.getSetterNames().contains("bean1"));
        Assert.assertEquals(true, metaObject.getGetterNames().contains("bean1"));
        Assert.assertEquals("11111", metaObject.getValue("col"));
        metaObject.setValue("col", "sdsadasd");
        Assert.assertEquals("sdsadasd", metaObject.getValue("col"));

    }

    @Test
    public void testHasSetter() throws Exception {

    }

    @Test
    public void testFindProperty() throws Exception {

    }

    @Test
    public void testFindProperty1() throws Exception {

        DemoBean demoBean = new DemoBean();
        demoBean.setCol("11111");

        MetaObject metaObject = new DefaultMetaObject(demoBean, GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        try {
            String str = metaObject.findProperty("bean1.bean2.col1");
            Assert.fail();
        }catch (Exception e){

        }
        String str = metaObject.findProperty("bean1.bean2.col2");
        Assert.assertEquals("bean1.bean2.col2", str);
    }

    @Test
    public void testMetaObjectForProperty() throws Exception {

    }

    @Test
    public void testGetObject() throws Exception {
        Map map = new HashMap();
        MetaObject metaObject = new DefaultMetaObject(map, GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        metaObject.instantiatePropertyValue("xxx");
        System.out.println(map);
    }
}