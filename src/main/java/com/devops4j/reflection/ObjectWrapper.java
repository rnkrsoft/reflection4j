package com.devops4j.reflection;

import com.devops4j.reflection.property.PropertyTokenizer;

import java.util.List;

public interface ObjectWrapper {
    /**
     * 根据属性获取值
     * @param prop 属性符号迭代器
     * @return 值
     */
    Object get(PropertyTokenizer prop);

    /**
     * 设置属性值
     * @param prop 属性符号迭代器
     * @param value 值
     */
    void set(PropertyTokenizer prop, Object value);

    /**
     * 根据输入的属性名获取对应目标对象的属性名
     * @param name 属性名，驼峰命名或者小花仙命名
     * @param useCamelCaseMapping 是否驼峰命名
     * @return 属性名
     */
    String findProperty(String name, boolean useCamelCaseMapping);

    /**
     * 获取Getter方法名列表
     * @return
     */
    String[] getGetterNames();

    /**
     * 获取Setter方法名列表
     * @return
     */
    String[] getSetterNames();

    /**
     * 根据属性名获取Setter方法的入参类型
     * @param name
     * @return
     */
    Class<?> getSetterType(String name);

    /**
     * 根据属性名获取Getter方法的入参类型
     * @param name
     * @return
     */
    Class<?> getGetterType(String name);

    /**
     * 获取是否有属性名对应的Setter方法
     * @param name
     * @return
     */
    boolean hasSetter(String name);

    /**
     * 获取是否有属性名对应的Getter方法
     * @param name
     * @return
     */
    boolean hasGetter(String name);

    /**
     * 是否为集合
     * @return
     */
    boolean isCollection();

    /**
     * 增加元素，只isCollection() 返回真值有效
     * @param element
     */
    void add(Object element);

    /**
     * 增加元素，只isCollection() 返回真值有效
     * @param element
     */
    <E> void addAll(List<E> element);

    /**
     * 获取原始对象
     * @param <T>
     * @return
     */
    <T> Object getNativeObject();

    MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory);
}