package com.devops4j.reflection.property;

import org.junit.Test;

/**
 * Created by devops4j on 2017/6/19.
 */
public class PropertyTokenizerTest {

    @Test
    public void testNext1() throws Exception {
        PropertyTokenizer tokenizer = new PropertyTokenizer("innerBean1[x].name");
        System.out.println(tokenizer.getName());
        System.out.println(tokenizer.getChildren());
        System.out.println(tokenizer.getIndex());
        System.out.println(tokenizer.getIndexedName());
    }

    @Test
    public void testNext2() throws Exception {
        PropertyTokenizer tokenizer = new PropertyTokenizer("beanName[x].fieldName1[y].fieldName2");
        System.out.println(tokenizer.getName());
        System.out.println(tokenizer.getChildren());
        System.out.println(tokenizer.getIndex());
        System.out.println(tokenizer.getIndexedName());
        {
            tokenizer = tokenizer.next();
            System.out.println(tokenizer.getName());
            System.out.println(tokenizer.getChildren());
            System.out.println(tokenizer.getIndex());
            System.out.println(tokenizer.getIndexedName());
        }
    }
}