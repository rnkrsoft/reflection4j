package com.rnkrsoft.reflection4j.meta;


import com.rnkrsoft.reflection4j.Invoker;
import com.rnkrsoft.reflection4j.MetaClass;
import com.rnkrsoft.reflection4j.GlobalSystemMetadata;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by rnkrsoft on 2017/7/14.
 */
public class DefaultMetaClassTest {
    @Test
    public void testBuildProperty() throws Exception {
        MetaClass metaClass = new DefaultMetaClass(DemoBean.class,GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY);
        String str = metaClass.findProperty("bean1.bean2.col2");
        Assert.assertEquals("bean1.bean2.col2", str);
        String str1 = metaClass.findProperty("bean1.bean2");
        Assert.assertEquals("bean1.bean2", str1);

        String str2 = metaClass.findProperty("bean1");
        Assert.assertEquals("bean1", str2);
    }

    @Test
    public void testMetaClassForProperty() throws Throwable {
        MetaClass metaClass = new DefaultMetaClass(DemoBean.class,GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY,  GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY);
        MetaClass bean1Meta1 = metaClass.metaClassForProperty("bean1");
        Assert.assertEquals(DemoBean1.class, bean1Meta1.getType());
        Invoker invoker = bean1Meta1.getReflector().getGetter("col1");
        DemoBean1 demoBean1 = new DemoBean1();
        demoBean1.setCol1("xxxxxxxxxxxxxxx");
        String col1 = invoker.invoke(demoBean1);
        Assert.assertEquals("xxxxxxxxxxxxxxx", col1);
        MetaClass bean1Meta2 = bean1Meta1.metaClassForProperty("bean2");
        Assert.assertEquals(DemoBean2.class, bean1Meta2.getType());
    }

    @Test
    public void testHasGetter() throws Exception {
        MetaClass metaClass = new DefaultMetaClass(DemoBean.class,GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY,  GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY);
        DemoBean demoBean = metaClass.newInstance();
        demoBean.setCol("xxxxxxxxxxxxxxxxxxxxxsds");
        String col = metaClass.getGetter("col").invoke(demoBean);
        Assert.assertEquals("xxxxxxxxxxxxxxxxxxxxxsds", col);
    }

    @Test
    public void testHasSetter() throws Exception {
        MetaClass metaClass = new DefaultMetaClass(DemoBean.class,GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY,  GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY);
        Assert.assertEquals(true, metaClass.hasSetter("col"));
        Assert.assertEquals(true, metaClass.hasGetter("col"));
        Assert.assertEquals(false, metaClass.hasSetter("col1"));
        Assert.assertEquals(false, metaClass.hasGetter("col1"));
    }

    @Test
    public void testGetGetterNames() throws Exception {
        MetaClass metaClass = new DefaultMetaClass(DemoBean.class,GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY,  GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY);
        Assert.assertEquals(true, metaClass.getGetterNames().contains("col"));
        Assert.assertEquals(false, metaClass.getGetterNames().contains("col1"));
    }

    @Test
    public void testGetSetterNames() throws Exception {
        MetaClass metaClass = new DefaultMetaClass(DemoBean.class,GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY, GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY);
        Assert.assertEquals(true, metaClass.getSetterNames().contains("col"));
        Assert.assertEquals(false, metaClass.getSetterNames().contains("col1"));
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
    public void test1() throws Throwable {
        MetaClass metaClass = new DefaultMetaClass(DemoBean.class,GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY,  GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY);
        Invoker invoker = metaClass.getGetter("col");
        DemoBean demoBean = new DemoBean();
        demoBean.setCol("xxxx");
        String name = invoker.invoke(demoBean);
        System.out.println(name);
        Assert.assertEquals("xxxx", name);
    }

    @Test
    public void test2() throws Throwable {
        MetaClass metaClass = new DefaultMetaClass(HashMap.class,GlobalSystemMetadata.OBJECT_FACTORY, GlobalSystemMetadata.OBJECT_WRAPPER_FACTORY,  GlobalSystemMetadata.REFLECTOR_FACTORY, GlobalSystemMetadata.META_CLASS_FACTORY);
    }
}