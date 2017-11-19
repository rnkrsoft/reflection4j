package com.devops4j.reflection.property;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by devops4j on 2017/7/13.
 */
public class PropertyCopierTest {

    @Test
    public void testCopyBeanProperties() throws Exception {
        DemoBean target = new DemoBean();
        PropertyCopier.copyBeanProperties(DemoBean.class, DemoBean.builder().name("test1").age(12).amt(BigDecimal.TEN).build() ,target);
        System.out.println(target);
    }

    @Test
    public void testCopyBeanProperties2() throws Exception {
        DemoBean2 target = new DemoBean2();
        PropertyCopier.copyBeanProperties(DemoBean.class, DemoBean.builder().name("test1").age(12).amt(BigDecimal.TEN).build() ,target);
        System.out.println(target);
    }

    @Test
    public void testCopyBeanProperties3() throws Exception {
        DemoBean2 source = new DemoBean2();
        source.setTarget("no");
        source.setAge(12);
        source.setAmt(BigDecimal.TEN);
        source.setName("test2");
        DemoBean target = new DemoBean();
        PropertyCopier.copyBeanProperties(DemoBean2.class, source ,target);
        System.out.println(target);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCopyBeanProperties_Fail() throws Exception {
        DemoBean target = new DemoBean();
        PropertyCopier.copyBeanProperties(DemoBean.class, DemoBean.builder().name("test1").age(12).amt(BigDecimal.TEN) ,target);
        System.out.println(target);
    }
}