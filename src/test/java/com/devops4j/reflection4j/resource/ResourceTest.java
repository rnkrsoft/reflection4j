package com.devops4j.reflection4j.resource;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by devops4j on 2017/12/18.
 */
public class ResourceTest {

    @Test
    public void testReadFileToString() throws Exception {
        Resource resource = new Resource();
        String str = resource.readFileToString("classpath*:com.devops4j.reflection4j.readme.md", "UTF-8");
        Assert.assertNotNull(str);
        String[] strs = str.split("\\n");
        Assert.assertEquals(4, strs.length);
        Assert.assertEquals("test1234", strs[0].trim());
        Assert.assertEquals("测试1234", strs[1].trim());
        Assert.assertEquals("test1234", strs[2].trim());
        Assert.assertEquals("测试1234", strs[3].trim());
    }
}