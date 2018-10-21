package com.rnkrsoft.reflection4j.meta;

import com.rnkrsoft.reflection4j.MetaClass;
import com.rnkrsoft.reflection4j.MetaObject;
import com.rnkrsoft.reflection4j.GlobalSystemMetadata;
import com.rnkrsoft.reflection4j.bean.RootBean;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rnkrsoft on 2017/12/3.
 */
public class DefaultMetaObjectTest {
    @Test
    public void testMetaObjectForProperty1() throws Exception {
        DemoBean1 demoBean1 = new DemoBean1();
        demoBean1.setCol1("11111");
        demoBean1.bean1 = new DemoBean2();
        demoBean1.bean1.bean2 = new DemoBean3();
        MetaObject metaObject = new DefaultMetaObject("", "", DemoBean1.class, demoBean1, GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        Assert.assertEquals(DemoBean1.class, metaObject.getType());
        MetaObject metaObject1 = metaObject.metaObjectForProperty("col1");
        Assert.assertEquals("col1", metaObject1.getFullName());
        Assert.assertEquals("11111", metaObject1.getObject());
        MetaObject metaObject2 = metaObject.metaObjectForProperty("bean1.bean2");
        Assert.assertEquals("bean1.bean2", metaObject2.getFullName());
        Assert.assertEquals(DemoBean3.class, metaObject2.getType());
        demoBean1.bean1.col2 = "ssss";
        MetaObject metaObject_ = new DefaultMetaObject("", "", DemoBean2.class, demoBean1.bean1, GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        MetaObject metaObject_1 = metaObject_.metaObjectForProperty("col2");
        Assert.assertEquals("col2", metaObject_1.getFullName());
        Assert.assertEquals(String.class, metaObject_1.getType());
    }

    @Test
    public void testInstantiatePropertyValue() throws Exception {
        DemoBean1 demoBean1 = new DemoBean1();
        demoBean1.setCol1("11111");
        demoBean1.bean1 = new DemoBean2();
        Assert.assertNull(demoBean1.bean1.bean2);
        MetaObject metaObject = new DefaultMetaObject("", "",DemoBean1.class,  demoBean1, GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        MetaObject metaObject1 = metaObject.instantiatePropertyValue("bean1.bean2");
        MetaObject metaObject2 = metaObject.instantiatePropertyValue("bean1.bean2.col3");
        System.out.println(metaObject1.getMetaClass());
        System.out.println(metaObject2.getObject());
//        Assert.assertNotNull(demoBean1.bean1.bean2);
    }


    @Test
    public void testGetMetaClass() throws Exception {
        DemoBean1 demoBean1 = new DemoBean1();
        demoBean1.setCol1("11111");

        MetaObject metaObject = new DefaultMetaObject("", "", DemoBean1.class, demoBean1, GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        MetaClass metaClass1 = metaObject.metaClassForProperty("bean1");
        Assert.assertEquals(DemoBean2.class, metaClass1.getType());
        System.out.println(metaClass1.getGetterNames());
        MetaClass metaClass2 = metaObject.metaClassForProperty("bean1.bean2");
        Assert.assertEquals(DemoBean3.class, metaClass2.getType());
        System.out.println(metaClass2.getGetterNames());
        MetaClass metaClass3 = metaObject.metaClassForProperty("bean1.bean2.col3");
        Assert.assertEquals(String.class, metaClass3.getType());
        System.out.println(metaClass3.getGetterNames());
    }

    @Data
    static class DemoBean1 {
        String col1;
        DemoBean2 bean1;
        List<String> list1;
    }

    @Data
    static class DemoBean2 {
        String col2;
        DemoBean3 bean2;
        List<String> list2;
    }

    @Data
    static class DemoBean3 {
        String col3;
        List<String> list3;
    }

    @Test
    public void testHasGetter() throws Exception {
        DemoBean1 demoBean1 = new DemoBean1();
        demoBean1.setCol1("11111");
        demoBean1.bean1 = new DemoBean2();
        demoBean1.bean1.bean2 = new DemoBean3();

        MetaObject metaObject = new DefaultMetaObject(new ArrayList(),DemoBean1.class, demoBean1, GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        Assert.assertEquals(3, metaObject.getSetterNames().size());
        Assert.assertEquals(true, metaObject.getSetterNames().contains("col1"));
        Assert.assertEquals(true, metaObject.getGetterNames().contains("col1"));
        Assert.assertEquals(true, metaObject.getSetterNames().contains("bean1"));
        Assert.assertEquals(true, metaObject.getGetterNames().contains("bean1"));
        Assert.assertEquals("11111", metaObject.getValue("col1"));
        metaObject.setValue("col1", "sdsadasd");
        Assert.assertEquals("sdsadasd", metaObject.getValue("col1"));

    }

    @Test
    public void testHasSetter() throws Exception {

    }

    @Test
    public void testFindProperty() throws Exception {
        MetaObject metaObject = GlobalSystemMetadata.forObject(RootBean.class, new RootBean());
        for (String getterName : metaObject.getGetterNames()) {
            MetaObject mo1 = metaObject.metaObjectForProperty(getterName);
            System.out.println(mo1.getFullName());
            for (String getterName1 : mo1.getGetterNames()) {
                MetaObject mo2 = metaObject.metaObjectForProperty(mo1.getFullName() + "." + getterName1);
                System.out.println(mo2.getFullName());
            }
        }
    }

    @Test
    public void testFindProperty1() throws Exception {

        DemoBean1 demoBean1 = new DemoBean1();
        demoBean1.setCol1("11111");
        demoBean1.bean1 = new DemoBean2();


        MetaObject metaObject = new DefaultMetaObject("", "", DemoBean1.class, demoBean1, GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        try {
            String str = metaObject.findProperty("bean1.bean2.col1");
            System.out.println(str);
            Assert.fail();
        }catch (Exception e){
            e.printStackTrace();
        }
        String str = metaObject.findProperty("bean1.bean2.col3");
        Assert.assertEquals("bean1.bean2.col3", str);

        MetaObject metaObject1 = metaObject.instantiatePropertyValue("bean1.bean2");
        Assert.assertEquals("bean1.bean2",metaObject1.getFullName());
    }

    @Test
    public void testMetaObjectForProperty() throws Exception {

    }

    @Test
    public void testGetObject() throws Exception {
        Map map = new HashMap();
        MetaObject metaObject = new DefaultMetaObject("", "", Map.class, map, GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        metaObject.instantiatePropertyValue("xxx");
        System.out.println(map);
    }
}