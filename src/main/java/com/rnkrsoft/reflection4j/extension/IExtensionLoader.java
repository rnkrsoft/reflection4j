package com.rnkrsoft.reflection4j.extension;

import java.util.Set;

/**
 * 拓展点加载点接口
 * 拓展点加载器需要完成如下场景的使用:
 * 1.在提供拓展点名称的情况下，提供单例的拓展点实例获得
 * 2.获取拓展点默认实现
 * 3.在提供拓展点的情况下，可以对拓展点实例进行增强
 * 4.在提供拓展点的情况下，可以创建多例实例
 * 5.在提供拓展类的情况下，可以获取单例的拓展点实例
 * 6.在提供拓展类的情况下，获取拓展点名称
 * 7.获取支持的拓展点名称列表
 * 8.获取已经加载的拓展点名称列表
 * 9.获取指定拓展点名称情况下，获取拓展点实现类类对象
 * 10.检查拓展点名称是否存在
 * 11.在已加载使用的拓展点实例，在后续无论什么情况下都能获取唯一的首次加载实例
 * 12.拓展点名称可以在/META-INF/extensions/下的xxx.xxxx.xxxx文件定义，形如name=yyy.yyy.yyyy;
 * 也可在/META-INF/extensions/下的xxx.xxxx.xxxx文件定义，形如yyy.yyy.yyyy 而在yyy.yyy.yyyy类上标注@Extension("name")指定名称
 *
 * @param <T>
 */
interface IExtensionLoader<T> {
    /**
     * 获取指定拓展点的实例，拓展点名称为空时默认加载@SPI指定的拓展点实例；找不到拓展点名称时抛出异常
     *
     * @param name 拓展点名称
     * @return 拓展点的实例
     * @throws IllegalStateException 当拓展点名称对应的实例不存在时抛出
     */
    T getExtension(String name);

    /**
     * 获取默认的拓展点实例，该实例名称由@SPI提供
     *
     * @return 拓展点的实例
     */
    T getExtension();

    /**
     * 根据类名获取拓展点实例
     *
     * @param className 类全限定名
     * @return 拓展点的实例
     */
    T getExtensionByClassName(String className);

    /**
     * 创建一个新的拓展实例，并且不缓存，非单例
     *
     * @param name 拓展点名称
     * @return 拓展点的实例
     */
    T newExtension(String name);

    /**
     * 获取已加载的全局拓展点实例，第一次加载的拓展点则为全局拓展点
     *
     * @param name 拓展点名称
     * @return 拓展点的实例
     * @throws IllegalArgumentException 拓展点名称第一次为必须传入，否则抛出异常；当加载后不再需要传入
     */
    T getGlobalExtension(final String name);

    /**
     * 获取已加载拓展点实例，不会进行拓展点的实例化过程
     *
     * @param name 拓展点名称
     * @return 拓展点实例
     * @throws IllegalArgumentException 拓展点名称为必须传入，否则抛出异常
     */
    T getLoadedExtension(String name);


    /**
     * 返回已加载的拓展点名称,只对扫描到的实现，显示调用的情况下才会返回，区别于{@link #getSupportedExtensions()} 支持的拓展点
     *
     * @return 拓展点名称集合
     * @see #getSupportedExtensions()
     */
    Set<String> getLoadedExtensions();


    /**
     * 返回所有支持的拓展点名称, 只要能够扫描到实现，都会在该方法返回，区别于{@link #getLoadedExtensions()} 已加载拓展点
     *
     * @return 拓展点名称集合
     * @see #getLoadedExtensions()
     */
    Set<String> getSupportedExtensions();


    /**
     * 通过类全限定名获取拓展点名称
     *
     * @param className 类全限定名
     * @return 拓展点名称
     */
    String getExtensionNameByClassName(String className);


    /**
     * 获取指定拓展点名称的实现类对象
     *
     * @param name 拓展点名称
     * @return 拓展点的实现类对象
     */
    Class<?> getExtensionClass(String name);


    /**
     * 检查是否存在拓展点名称
     *
     * @param name 拓展点名称
     * @return 如果存在返回真
     */
    boolean hasExtension(String name);
}