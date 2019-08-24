package com.rnkrsoft.reflection4j.extension;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by rnkrsoft.com on 2019/8/23.
 */
public class ExtensionLoaderTest {

    @Test
    public void testGetExtensionLoader() throws Exception {
        ExtensionLoader<DemoService> extensionLoader = ExtensionLoader.getExtensionLoader(DemoService.class);
        DemoService demoService1 = extensionLoader.getExtension("cn");
        String say1 = demoService1.say();
        Assert.assertEquals("你好!", say1);

        DemoService demoService2 = extensionLoader.getExtension("en");
        String say2 = demoService2.say();
        Assert.assertEquals("Hello!", say2);
    }


    @Test
    public void testGetExtensionLoader2() throws Exception {
        ExtensionLoader<DemoService> extensionLoader = ExtensionLoader.getExtensionLoader(DemoService.class);
        extensionLoader.getExtension("cn");
        DemoService demoService1 = extensionLoader.getExtension();
        String say1 = demoService1.say();
        Assert.assertEquals("你好!", say1);
    }
}