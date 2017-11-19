package com.devops4j.reflection.property;

import lombok.*;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by devops4j on 2017/7/13.
 */
public class PropertyCopierTest {
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
    public void testCopyBeanProperties() throws Exception {
        DemoBean target = new DemoBean();
        PropertyCopier.copyBeanProperties(DemoBean.class, DemoBean.builder().name("test1").age(12).amt(BigDecimal.TEN).build() ,target);
        System.out.println(target);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCopyBeanProperties_Fail() throws Exception {
        DemoBean target = new DemoBean();
        PropertyCopier.copyBeanProperties(DemoBean.class, DemoBean.builder().name("test1").age(12).amt(BigDecimal.TEN) ,target);
        System.out.println(target);
    }
}