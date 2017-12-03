package com.devops4j.reflection4j;

import java.util.Collection;

/**
 * Created by devops4j on 2017/5/28.
 * 类元信息
 */
public interface MetaClass {
    /**
     * 获取该类的字段对应的类元信息
     * @param propertyName 属性名
     * @return 类元信息
     */
    MetaClass metaClassForProperty(String propertyName);
    /**
     * 返回是否有Getter方法
     *
     * @param propertyName 字段名
     * @return 如果存在返回真
     */
    boolean hasGetter(String propertyName);

    /**
     * 返回是否有Setter方法
     *
     * @param propertyName 字段名
     * @return 如果存在返回真
     */
    boolean hasSetter(String propertyName);

    /**
     * 返回该类对应的所有Getter方法
     *
     * @return Getter方法列表
     */
    Collection<String> getGetterNames();

    /**
     * 返回该类对应的所有Setter方法
     *
     * @return Setter方法列表
     */
    Collection<String> getSetterNames();

    /**
     * 查找属性名
     * 形如beanNam1.beanName2
     * 形如beanNam1[0].beanName2
     *
     * @param propertyName 属性名
     * @return
     */
    String findProperty(String propertyName);

    /**
     * 查找属性名
     *
     * @param propertyName                属性名
     * @param useCamelCaseMapping 是否驼峰命名
     * @return
     */
    String findProperty(String propertyName, boolean useCamelCaseMapping);

    /**
     * 根据字段名提取属性名称，可能返回很复杂的名称
     *
     * @param propertyName
     * @param builder
     * @return
     */
    StringBuilder buildProperty(String propertyName, StringBuilder builder);

    /**
     * 获取指定方法的执行器
     * @param propertyName
     * @return
     */
    Invoker getMethodInvoker(String propertyName, Class... classes);
    /**
     * 获取Getter执行器
     *
     * @param propertyName 字段名
     * @return 执行器
     */
    Invoker getGetter(String propertyName);

    /**
     * 获取Setter执行器
     *
     * @param propertyName 字段名
     * @return 执行器
     */
    Invoker getSetter(String propertyName);

    /**
     * 获取Getter返回值类型
     *
     * @param propertyName 属性名
     * @return 类型
     */
    Class<?> getGetterType(String propertyName);

    /**
     *  获取Setter返回值类型
     *
     * @param propertyName 属性名
     * @return
     */
    Class<?> getSetterType(String propertyName);

    /**
     * 是否存在默认构造函数
     *
     * @return 返回真
     */
    boolean hasDefaultConstructor();

    /**
     * 创建一个类元信息对应的实例
     * @param <T> 实例
     * @return 实例
     */
    <T> T newInstance();

    /**
     * 获取类元信息对应的类对象
     * @return 类对象
     */
    Class getType();

    /**
     * 获取类元信息对应的反射器
     * @return 反射器
     */
    Reflector getReflector();
}
