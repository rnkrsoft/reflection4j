package com.rnkrsoft.reflection4j.resource;

import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.List;

/**
 * Created by rnkrsoft on 2017/12/18.
 */
public class ResourceTest {

    @Test
    public void testReadFileToString() throws Exception {
        String str = Resource.readFileToString("classpath:com.rnkrsoft.reflection4j.readme.md", "UTF-8");
        Assert.assertNotNull(str);
        String[] strs = str.split("\\n");
        Assert.assertEquals(4, strs.length);
        Assert.assertEquals("test1234", strs[0].trim());
        Assert.assertEquals("测试1234", strs[1].trim());
        Assert.assertEquals("test1234", strs[2].trim());
        Assert.assertEquals("测试1234", strs[3].trim());
    }

    @Test
    public void testGetFiles() throws Exception {
        List<URL> urlList = Resource.getFiles("classpath*:com.rnkrsoft.reflection4j.readme.md");
        Assert.assertEquals(1, urlList.size());
    }

    @Test
    public void testGetFiles1() throws Exception {
        List<URL> urlList = Resource.getFiles(null, "classpath*:com.rnkrsoft.reflection4j.readme.md");
        Assert.assertEquals(1, urlList.size());
    }

    @Test
    public void testGetFile() throws Exception {
        URL url = Resource.getFile("classpath:com.rnkrsoft.reflection4j.readme.md");
        Assert.assertNotNull(url);
    }
}