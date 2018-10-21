package com.rnkrsoft.reflection4j.wrapper;

import com.rnkrsoft.reflection4j.GlobalSystemMetadata;
import com.rnkrsoft.reflection4j.ObjectWrapper;
import com.rnkrsoft.reflection4j.property.PropertyTokenizer;
import lombok.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rnkrsoft on 2017/7/12.
 */
public class BeanWrapperTest {
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

    @Test
    public void testGet() throws Exception {
        DemoBean demoBean = new DemoBean();
        demoBean.setName("wwwww");

        ObjectWrapper objectWrapper = new BeanWrapper(new ArrayList(), DemoBean.class, GlobalSystemMetadata.META_OBJECT_FACTORY.forObject("", DemoBean.class, demoBean), demoBean, GlobalSystemMetadata.META_CLASS_FACTORY, GlobalSystemMetadata.META_OBJECT_FACTORY);
        System.out.println(Arrays.asList(objectWrapper.getGetterNames()));
        System.out.println(Arrays.asList(objectWrapper.getSetterNames()));
        PropertyTokenizer prop = new PropertyTokenizer("name");
        Object value = objectWrapper.get(prop);
        System.out.println(value);
        objectWrapper.set(prop, "yyyy");
        objectWrapper.set(new PropertyTokenizer("age"), 20);
        value = objectWrapper.get(prop);
        System.out.println(value);
        System.out.println(demoBean.getName());
        System.out.println(demoBean.getAge());
    }

}