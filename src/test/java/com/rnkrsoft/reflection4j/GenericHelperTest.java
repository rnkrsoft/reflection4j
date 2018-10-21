package com.rnkrsoft.reflection4j;

import com.rnkrsoft.reflection4j.generic.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by rnkrsoft on 2017/12/2.
 */
public class GenericHelperTest {

    @Test
    public void testExtractInterface() throws Exception {
        {
            Class[] classes = GenericHelper.extractInterface(Interface2.class, Interface1.class);
            System.out.println(classes[0]);
            System.out.println(classes[1]);
        }
        {
            Class[] classes = GenericHelper.extractInterface(Interface2.class, Interface0.class);
            System.out.println(classes[0]);
            System.out.println(classes[1]);
        }
        {
            Class[] classes = GenericHelper.extractInterface(Interface1.class, Interface0.class);
            System.out.println(classes[0]);
            System.out.println(classes[1]);
            System.out.println(classes[2]);
        }

    }

    @Test
    public void testExtractInterfaceMethodParams() throws Exception {
        Class[] classes = GenericHelper.extractInterfaceMethodParams(Interface2.class, "doing", new Class[]{Request1.class}, 0);
        Assert.assertEquals(1, classes.length);
        Assert.assertEquals(String.class, classes[0]);

        Class[] classes1 = GenericHelper.extractInterfaceMethodParams(Interface2.class, "work", new Class[]{Request2.class}, 0);
        Assert.assertEquals(2, classes1.length);
        Assert.assertEquals(String.class, classes1[0]);
        Assert.assertEquals(Integer.class, classes1[1]);
    }

    @Test
    public void testExtractInterfaceMethodReturn() throws Exception {
        Class[] classes = GenericHelper.extractInterfaceMethodReturn(Interface2.class, "doing", new Class[]{Request1.class});
        Assert.assertEquals(1, classes.length);
        Assert.assertEquals(Integer.class, classes[0]);

        Class[] classes1 = GenericHelper.extractInterfaceMethodReturn(Interface2.class, "work", new Class[]{Request2.class});
        Assert.assertEquals(3, classes1.length);
        Assert.assertEquals(String.class, classes1[0]);
        Assert.assertEquals(Integer.class, classes1[1]);
        Assert.assertEquals(Integer.class, classes1[2]);
    }
}