package com.rnkrsoft.reflection4j.bean;

import com.rnkrsoft.reflection4j.GlobalSystemMetadata;
import com.rnkrsoft.reflection4j.MetaClass;
import com.rnkrsoft.reflection4j.MetaObject;
import org.junit.Test;

/**
 * Created by rnkrsoft.com on 2018/10/20.
 */
public class MetadataTest {
    @Test
    public void test1() {
        MetaClass metaClass = GlobalSystemMetadata.forClass(RootBean.class);
        MetaClass metaClass1 = metaClass.metaClassForProperty("bean1");
        MetaClass metaClass2 = metaClass.metaClassForProperty("bean1.bean2");
        MetaClass metaClass3 = metaClass.metaClassForProperty("bean1.bean2.name2");

        Object bean = metaClass.newInstance();
        System.out.println(bean);

        Object bean1 = metaClass1.newInstance();
        System.out.println(bean1);

        MetaObject metaObject = GlobalSystemMetadata.forObject(RootBean.class, new RootBean());
        MetaObject metaObject2 =  metaObject.metaObjectForProperty("bean1.bean2");
        System.out.println(metaObject2.getFullName());

        MetaObject metaObject3 =  metaObject.metaObjectForProperty("bean1.bean2.name2");
        System.out.println(metaObject3.getFullName());

    }
}
