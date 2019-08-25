# ExtensionLoader加载器
拓展点加载器参照Dubbo中的ExtensionLoader实现，并对其发展，增加@Wrapper,@Extension注解，支持单例和多例构建。

```
拓展点加载器需要完成如下场景的使用:
1.在提供拓展点名称的情况下，提供单例的拓展点实例获得
2.获取拓展点默认实现标注@SPI
3.在提供拓展点的情况下，可以对拓展点实例进行增强
4.在提供拓展点的情况下，可以创建多例实例
5.在提供拓展类的情况下，可以获取单例的拓展点实例
6.在提供拓展类的情况下，获取拓展点名称
7.获取支持的拓展点名称列表
8.获取已经加载的拓展点名称列表
9.获取指定拓展点名称情况下，获取拓展点实现类类对象
10.检查拓展点名称是否存在
11.在已加载使用的拓展点实例，在后续无论什么情况下都能获取唯一的首次加载实例
12.拓展点名称可以在/META-INF/extensions/下的xxx.xxxx.xxxx文件定义，形如name=yyy.yyy.yyyy;
也可在/META-INF/extensions/下的xxx.xxxx.xxxx文件定义，形如yyy.yyy.yyyy 而在yyy.yyy.yyyy类上标注@Extension("name")指定名称
```

## API说明

### @SPI 定义拓展点的默认实现

```java
package com.rnkrsoft.reflection4j.extension;

import java.lang.annotation.*;

/**
 * 扩展点接口的标识。
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {

    /**
     * 缺省扩展点名。
     */
    String value() default "";

}
```



### @Extension 定义拓展点

```java
package com.rnkrsoft.reflection4j.extension;

import java.lang.annotation.*;

/**
 * Created by rnkrsoft.com on 2019/8/24.
 * 用于定义拓展点实现类上，用于定义拓展点名称
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Extension {
    /**
     * 拓展点名称
     *
     * @return
     */
    String value();
}

```

### @Wrapper 包装注解

```java
package com.rnkrsoft.reflection4j.extension;

import java.lang.annotation.*;

/**
 * Created by rnkrsoft.com on 2019/8/24.
 * 包装拓展点服务
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Wrapper {
    /**
     * 优先级，默认为-1，表示按照拓展加载器内部顺序随机排，否在优先级大，约在包装外层
     * @return 优先级
     */
    int priority() default -1;
}

```



### ExtensionLoader方法

| 方法名                              | 使用说明                                                     |
| ----------------------------------- | ------------------------------------------------------------ |
| getExtension(String)                | 在提供拓展点名称的情况下，提供单例的拓展点实例获得           |
| getExtension()                      | 获取默认的拓展点实例，该实例名称由@SPI提供                   |
| getExtensionByClassName(String)     | 根据类名获取拓展点实例                                       |
| getGlobalExtension(String)          | 获取已加载的全局拓展点实例，第一次加载的拓展点则为全局拓展点 |
| getLoadedExtension(String)          | 获取已加载拓展点实例，不会进行拓展点的实例化过程             |
| getLoadedExtensions()               | 返回已加载的拓展点名称,只对扫描到的实现，显示调用的情况下才会返回，区别于getSupportedExtensions()支持的拓展点 |
| getSupportedExtensions()            | 返回所有支持的拓展点名称, 只要能够扫描到实现，都会在该方法返回，区别于getLoadedExtensions()已加载拓展点 |
| getExtensionNameByClassName(String) | 通过类全限定名获取拓展点名称                                 |
| getExtensionClass(String)           | 获取指定拓展点名称的实现类对象                               |
| hasExtension(String)                | 检查是否存在拓展点名称                                       |

## 例子

### 拓展点使用

通过建立DemoService接口，实现说中文和英文的实现，在调用代码中，按照拓展点名称来获取实例

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
@Extension("cn")
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
@Extension("en")
public class EnDemoService implements DemoService{
    @Override
    public String say() {
        return "Hello!";
    }
}

```



在META-INF/extensions/下创建文件com.rnkrsoft.reflection4j.extension.DemoService

```
com.rnkrsoft.reflection4j.extension.CnDemoService
com.rnkrsoft.reflection4j.extension.EnDemoService
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



### 演示包装增强

```java
package com.rnkrsoft.reflection4j.extension;

/**
 * Created by rnkrsoft.com on 2019/8/24.
 */
@Extension("x1")
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
@Extension("x2")
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
com.rnkrsoft.reflection4j.extension.CnDemoService
com.rnkrsoft.reflection4j.extension.EnDemoService
com.rnkrsoft.reflection4j.extension.Wrapper2DemoService  #新增加的
com.rnkrsoft.reflection4j.extension.Wrapper1DemoService  #新增加的
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



### 根据类名获取拓展点名称

```java
@Test
public void testGetExtensionNameByClassName() throws Exception {
    ExtensionLoader<DemoService> extensionLoader = ExtensionLoader.getExtensionLoader(DemoService.class);
    String name1 = extensionLoader.getExtensionNameByClassName("com.rnkrsoft.reflection4j.extension.CnDemoService");
    Assert.assertEquals("cn",name1);
    String name2 = extensionLoader.getExtensionNameByClassName("com.rnkrsoft.reflection4j.extension.EnDemoService");
    Assert.assertEquals("en",name2);
}
```



### 根据类名获取拓展点实例

```java
@Test
public void testGetExtensionByClassName() throws Exception {
    ExtensionLoader<DemoService> extensionLoader = ExtensionLoader.getExtensionLoader(DemoService.class);
    DemoService demoService1 = extensionLoader.getExtensionByClassName("com.rnkrsoft.reflection4j.extension.CnDemoService");
    String say1 = demoService1.say();
    Assert.assertEquals("你好!", say1);

    DemoService demoService2 = extensionLoader.getExtensionByClassName("com.rnkrsoft.reflection4j.extension.EnDemoService");
    String say2 = demoService2.say();
    Assert.assertEquals("Hello!", say2);
}
```



