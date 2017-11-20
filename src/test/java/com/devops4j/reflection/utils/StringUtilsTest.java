package com.devops4j.reflection.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by devops4j on 2017/7/28.
 */
public class StringUtilsTest {

    @Test
    public void testToFirstLowerCase() throws Exception {
        assertEquals("a", StringUtils.toFirstLowerCase("A"));
        assertEquals("a", StringUtils.toFirstLowerCase("a"));
        assertEquals("1", StringUtils.toFirstLowerCase("1"));
        assertEquals("ab", StringUtils.toFirstLowerCase("ab"));
        assertEquals("ab", StringUtils.toFirstLowerCase("Ab"));
        assertEquals("aB", StringUtils.toFirstLowerCase("AB"));
    }

    @Test
    public void testToFirstUpperCase() throws Exception {
        assertEquals("A", StringUtils.toFirstUpperCase("A"));
        assertEquals("A", StringUtils.toFirstUpperCase("a"));
        assertEquals("1", StringUtils.toFirstUpperCase("1"));
        assertEquals("Ab", StringUtils.toFirstUpperCase("ab"));
        assertEquals("Ab", StringUtils.toFirstUpperCase("Ab"));
        assertEquals("AB", StringUtils.toFirstUpperCase("aB"));
    }

    @Test
    public void testToCamelCase() throws Exception {
        assertEquals("thisIsTest", StringUtils.toCamelCase("this_is_test"));
    }
}