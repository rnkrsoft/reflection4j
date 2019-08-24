# reflection4j
一个用于反射调用Java对象的工具包，提取自mybatis
1.提供对某一个类或者对象的包装，可返回某一方法的执行器，并能够执行
2.提供包路径下类的扫描
3.提供包路径下资源的加载
4.提供类的别名管理
5.拓展点加载器，提取自dubbo,同时根据实际需要进行了改造

![reflection4j](reflection4j.svg)

[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.rnkrsoft.reflection4j/reflection4j/badge.svg)](http://search.maven.org/#search|ga|1|g%3A%22com.rnkrsoft.reflection4j%22%20AND%20a%3A%22reflection4j%22)


# 拓展点加载器
提取自dubbo,保留懒加载实例化和包装实现，去除@Activate和@Adaptive。增加@Wrapper注解，对包装实例进行排序定义

拓展点接口定义

```java
package com.rnkrsoft.reflection4j.extension;

/**
 * Created by rnkrsoft.com on 2019/8/23.
 */
@SPI("cn")
public interface DemoService {
    String say();
}
```



拓展点实现1

```java
package com.rnkrsoft.reflection4j.extension;

/**
 * Created by rnkrsoft.com on 2019/8/23.
 */
public class CnDemoService implements DemoService{
    @Override
    public String say() {
        return "你好!";
    }
}
```

拓展点实现2

```java
package com.rnkrsoft.reflection4j.extension;

/**
 * Created by rnkrsoft.com on 2019/8/23.
 */
public class EnDemoService implements DemoService{
    @Override
    public String say() {
        return "Hello!";
    }
}
```



在META-INF/extensions/下创建文件com.rnkrsoft.reflection4j.extension.DemoService

```
cn=com.rnkrsoft.reflection4j.extension.CnDemoService
en=com.rnkrsoft.reflection4j.extension.EnDemoService
```



调用代码

```java
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

```



通过以上代码实现按需加载拓展点实现。
可参照https://www.cnblogs.com/Non-Tecnology/p/6882903.html



## 包装增强拓展点

在前面的基础上定义如下类

```java
package com.rnkrsoft.reflection4j.extension;

/**
 * Created by rnkrsoft.com on 2019/8/24.
 */
@Wrapper(priority = 1)
public class Wrapper1DemoService implements DemoService{
    DemoService instance;
    public Wrapper1DemoService(DemoService instance){
        this.instance = instance;
    }

    @Override
    public String say() {
        System.out.println("Wrapper1DemoService before");
        String value = instance.say();
        System.out.println("Wrapper1DemoService return");
        return value;
    }
}

```



```java
package com.rnkrsoft.reflection4j.extension;

/**
 * Created by rnkrsoft.com on 2019/8/24.
 */
@Wrapper(priority = 2)
public class Wrapper2DemoService implements DemoService{
    DemoService instance;
    public Wrapper2DemoService(DemoService instance){
        this.instance = instance;
    }

    @Override
    public String say() {
        System.out.println("Wrapper2DemoService before");
        String value = instance.say();
        System.out.println("Wrapper2DemoService return");
        return value;
    }
}

```



在

在META-INF/extensions/下创建文件com.rnkrsoft.reflection4j.extension.DemoService

```
cn=com.rnkrsoft.reflection4j.extension.CnDemoService
en=com.rnkrsoft.reflection4j.extension.EnDemoService
x2=com.rnkrsoft.reflection4j.extension.Wrapper2DemoService  #新增加的
x1=com.rnkrsoft.reflection4j.extension.Wrapper1DemoService  #新增加的
```



调用代码验证

```java
    @Test
    public void testGetExtensionLoader2() throws Exception {
        ExtensionLoader<DemoService> extensionLoader = ExtensionLoader.getExtensionLoader(DemoService.class);
        extensionLoader.getExtension("cn");
        DemoService demoService1 = extensionLoader.getExtension();
        String say1 = demoService1.say();
        Assert.assertEquals("你好!", say1);
    }
```

执行后，界面输出

```
Wrapper2DemoService before
Wrapper1DemoService before
Wrapper1DemoService return
Wrapper2DemoService return
```

