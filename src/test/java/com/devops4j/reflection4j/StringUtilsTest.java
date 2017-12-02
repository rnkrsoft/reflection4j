package com.devops4j.reflection4j;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by devops4j on 2017/7/28.
 */
public class StringUtilsTest {

    @Test
    public void testToFirstLowerCase() throws Exception {
        assertEquals("a", Utils.toFirstLowerCase("A"));
        assertEquals("a", Utils.toFirstLowerCase("a"));
        assertEquals("1", Utils.toFirstLowerCase("1"));
        assertEquals("ab", Utils.toFirstLowerCase("ab"));
        assertEquals("ab", Utils.toFirstLowerCase("Ab"));
        assertEquals("aB", Utils.toFirstLowerCase("AB"));
    }

    @Test
    public void testToFirstUpperCase() throws Exception {
        assertEquals("A", Utils.toFirstUpperCase("A"));
        assertEquals("A", Utils.toFirstUpperCase("a"));
        assertEquals("1", Utils.toFirstUpperCase("1"));
        assertEquals("Ab", Utils.toFirstUpperCase("ab"));
        assertEquals("Ab", Utils.toFirstUpperCase("Ab"));
        assertEquals("AB", Utils.toFirstUpperCase("aB"));
    }

    @Test
    public void testToCamelCase() throws Exception {
        assertEquals("thisIsTest", Utils.toCamelCase("this_is_test"));
    }
}