package com.devops4j.reflection;

import java.util.Map;

/**
 * Created by devops4j on 2017/7/12.
 * 类别名注册
 */
public interface TypeAliasRegistry {
    /**
     * 以别名获取类对象
     *
     * @param alias 别名
     * @param <T>   类对象
     * @return 类对象
     */
    <T> Class<T> resolveAlias(String alias);

    /**
     * 将指定包路径下，所有类以类名或者别名注解指定注册别名
     *
     * @param packageName 包路径
     */
    void registerAliases(String packageName);

    /**
     * 将指定包路径下，所有superType为基类的类以类名或者别名注解指定注册别名
     *
     * @param packageName 包路径
     * @param superType   基类
     */
    void registerAliases(String packageName, Class<?> superType);

    /**
     * 通过注解读取别名信息
     *
     * @param type 包含有别名注解的类对象，如果没有将使用类名作为别名
     */
    void registerAlias(Class<?> type);

    /**
     * 注册别名
     *
     * @param alias 别名
     * @param clazz 类对象
     */
    void registerAlias(String alias, Class<?> clazz);

    /**
     * 注册别名
     *
     * @param alias     别名
     * @param className 类全限定名
     */
    void registerAlias(String alias, String className);

    /**
     * 获取所有已注册别名对应的类对象
     *
     * @return
     */
    Map<String, Class<?>> getTypeAliases();
}
