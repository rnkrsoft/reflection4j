package com.devops4j.reflection4j;

import java.util.List;

/**
 * Created by devops4j on 2017/5/16.
 * 用于对实体类进行包装，提供字段、方法的元信息
 */
public interface MetaObject {
    /**
     * 返回是否有Getter方法
     *
     * @param fieldName 字段名
     * @return 如果存在返回真
     */
    boolean hasGetter(String fieldName);

    /**
     * 返回是否有Setter方法
     *
     * @param fieldName 字段名
     * @return 如果存在返回真
     */
    boolean hasSetter(String fieldName);

    /**
     * 设置字段值
     *
     * @param fieldName 字段名
     * @param value     字段值
     */
    void setValue(String fieldName, Object value);

    /**
     * 获取字段值
     *
     * @param fieldName 字段名
     * @param <T>       字段值
     * @return 字段值
     */
    <T> T getValue(String fieldName);

    /**
     * 返回Setter方法的类型
     *
     * @param fieldName 字段名
     * @return Setter方法的类型
     */
    Class<?> getSetterType(String fieldName);

    /**
     * 返回Getter方法的类型
     *
     * @param fieldName 字段名
     * @return Getter方法的类型
     */
    Class<?> getGetterType(String fieldName);

    String[] getGetterNames();

    String[] getSetterNames();

    /**
     * @param propName
     * @param useCamelCaseMapping
     * @return
     */
    String findProperty(String propName, boolean useCamelCaseMapping);

    Object getOriginalObject();

    boolean isCollection();

    <E> void addAll(List<E> list);

    void add(Object element);

    ReflectorFactory getReflectorFactory();

    ObjectFactory getObjectFactory();

    ObjectWrapperFactory getObjectWrapperFactory();

    MetaObject metaObjectForProperty(String property);

}
