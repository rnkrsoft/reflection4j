package com.rnkrsoft.reflection4j;

import com.rnkrsoft.reflection4j.factory.MetaClassFactory;
import com.rnkrsoft.reflection4j.factory.MetaObjectFactory;

import java.util.Collection;
import java.util.List;

/**
 * Created by rnkrsoft on 2017/5/16.
 * 用于对实体类进行包装，提供字段、方法的元信息
 */
public interface MetaObject {
    Class getType();
    /**
     * 获取当前值对象的完整名称，与PropertyTokenizer格式一致
     * @return
     */
    String getFullName();
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
     * 设置字段值
     *
     * @param propertyName 字段名
     * @param value     字段值
     */
    void setValue(String propertyName, Object value);

    /**
     * 获取字段值
     *
     * @param propertyName 字段名
     * @param <T>       字段值
     * @return 字段值
     */
    <T> T getValue(String propertyName);

    /**
     * 返回Setter方法的类型
     *
     * @param propertyName 字段名
     * @return Setter方法的类型
     */
    Class<?> getSetterType(String propertyName);

    /**
     * 返回Getter方法的类型
     *
     * @param propertyName 字段名
     * @return Getter方法的类型
     */
    Class<?> getGetterType(String propertyName);

    /**
     * 返回该类对应的所有Setter方法
     *
     * @return Setter方法列表
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
     * 形如beanNam1.beanName2
     * 形如beanNam1[0].beanName2
     *
     * @param propertyName 属性名
     * @param useCamelCaseMapping 是否转换为驼峰
     * @return
     */
    String findProperty(String propertyName, boolean useCamelCaseMapping);

    /**
     * 获取包装的对象
     * @return 原生对象
     */
    <T> Object getObject();

    boolean isCollection();

    <E> void addAll(List<E> list);

    void add(Object element);

    /**
     * 实例化属性为Bean的
     * @param propertyName
     */
    MetaObject instantiatePropertyValue(String propertyName);

    ReflectorFactory getReflectorFactory();

    ObjectFactory getObjectFactory();

    ObjectWrapperFactory getObjectWrapperFactory();
    MetaClassFactory getMetaClassFactory();
    MetaObjectFactory getMetaObjectFactory();

    /**
     * 获取属性值对应的对象元信息
     * @param propertyName 属性名
     * @return 对象元信息
     */
    MetaObject metaObjectForProperty(String propertyName);

    MetaClass metaClassForProperty(String propertyName);
    MetaClass getMetaClass();
}
