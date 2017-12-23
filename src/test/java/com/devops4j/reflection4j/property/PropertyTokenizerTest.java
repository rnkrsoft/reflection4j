package com.devops4j.reflection4j.property;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by devops4j on 2017/6/19.
 */
public class PropertyTokenizerTest {

    @Test
    public void testNext1() throws Exception {
        PropertyTokenizer tokenizer = new PropertyTokenizer("innerBean1[1].name");
        Assert.assertEquals("innerBean1", tokenizer.getName());
        Assert.assertEquals("name", tokenizer.getChildren());
        Assert.assertEquals("1", tokenizer.getIndex());
        Assert.assertEquals("innerBean1[1]", tokenizer.getIndexedName());
    }

    @Test
    public void testNext2() throws Exception {
        PropertyTokenizer tokenizer = new PropertyTokenizer("beanName[1].fieldName1[1].fieldName2");
        Assert.assertEquals("beanName", tokenizer.getName());
        Assert.assertEquals("fieldName1[1].fieldName2", tokenizer.getChildren());
        Assert.assertEquals("1", tokenizer.getIndex());
        Assert.assertEquals("beanName[1]", tokenizer.getIndexedName());
        tokenizer = tokenizer.next();
        Assert.assertEquals("fieldName1", tokenizer.getName());
        Assert.assertEquals("fieldName2", tokenizer.getChildren());
        Assert.assertEquals("1", tokenizer.getIndex());
        Assert.assertEquals("fieldName1[1]", tokenizer.getIndexedName());
    }
}