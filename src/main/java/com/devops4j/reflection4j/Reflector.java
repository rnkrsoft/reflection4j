package com.devops4j.reflection4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created by devops4j on 2017/5/28.
 */
public interface Reflector {
    /**
     * 获取反射的目标类
     *
     * @return 类对象
     */
    Class getType();

    /**
     * 获取无参构造函数构造器
     *
     * @return 函数构造器
     */
    Constructor getDefaultConstructor();

    /**
     * 是否存在午餐
     *
     * @return
     */
    boolean hasDefaultConstructor();

    /**
     * 获取方法执行器
     * @param name 方法名
     * @param paramTypes 参数类型
     * @return 执行器
     */
    Invoker getMethodInvoker(String name, Class... paramTypes);
    /**
     * 根据属性名获取执行器
     *
     * @param propertyName 属性名
     * @return 执行器
     */
    Invoker getSetter(String propertyName);

    /**
     * 根据属性名获取执行器
     *
     * @param propertyName 属性名
     * @return 执行器
     */
    Invoker getGetter(String propertyName);

    /**
     * 根据属性名获取类对象
     *
     * @param propertyName 属性名
     * @return 类型
     */
    Class<?> getSetterType(String propertyName);

    /**
     * 根据属性名获取类对象
     *
     * @param propertyName 属性名
     * @return 类型
     */
    Class<?> getGetterType(String propertyName);

    /**
     * 获取可进行Get属性名数组
     *
     * @return 属性名数组
     */
    Collection<String> getGettablePropertyNames();

    /**
     * 获取可进行Set属性名数组
     *
     * @return 属性名数组
     */
    Collection<String> getSettablePropertyNames();

    /**
     * 根据属性名检测是否有Setter
     *
     * @param propertyName 属性名
     * @return 布尔值
     */
    boolean hasSetter(String propertyName);

    /**
     * 根据属性名检测是否有Getter
     *
     * @param propertyName 属性名
     * @return 布尔值
     */
    boolean hasGetter(String propertyName);

    /**
     * 检测是否存在属性
     * @param propertyName
     * @return
     */
    boolean hasProperty(String propertyName);

    /**
     * 根据属性名获取Field对象
     * @param propertyName 属性名
     * @return Field对象
     */
    Field getField(String propertyName);
}
