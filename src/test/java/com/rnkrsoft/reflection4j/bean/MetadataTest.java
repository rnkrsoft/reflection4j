package com.rnkrsoft.reflection4j.bean;

import com.rnkrsoft.reflection4j.GlobalSystemMetadata;
import com.rnkrsoft.reflection4j.MetaClass;
import com.rnkrsoft.reflection4j.MetaObject;
import org.junit.Test;

/**
 * Created by woate on 2018/10/20.
 */
public class MetadataTest {
    @Test
    public void test1() {
        MetaClass metaClass = GlobalSystemMetadata.forClass(RootBean.class);
        MetaClass metaClass1 = metaClass.metaClassForProperty("bean1");
        MetaClass metaClass2 = metaClass.metaClassForProperty("bean1.bean1bean2");
        MetaClass metaClass3 = metaClass.metaClassForProperty("bean1.bean1bean2.name");

        Object bean = metaClass.newInstance();
        System.out.println(bean);

        Object bean1 = metaClass1.newInstance();
        System.out.println(bean1);

        MetaObject metaObject = metaClass1.getMetaObject(bean1);
        Object object = metaObject.getValue("bean1bean2");
        System.out.println(object);
        metaObject.setValue("name", "this is a test");
        System.out.println(metaObject.getValue("name"));

    }
}
