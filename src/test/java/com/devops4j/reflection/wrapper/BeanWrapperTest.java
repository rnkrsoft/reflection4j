package com.devops4j.reflection.wrapper;

import com.devops4j.reflection.ObjectWrapper;
import com.devops4j.reflection.factory.MetaObjectFactory;
import com.devops4j.reflection.property.PropertyTokenizer;
import lombok.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by devops4j on 2017/7/12.
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
        ObjectWrapper objectWrapper = new BeanWrapper(MetaObjectFactory.forObject(demoBean), demoBean);
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