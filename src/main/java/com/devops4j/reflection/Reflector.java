package com.devops4j.reflection;

import java.lang.reflect.Constructor;

/**
 * Created by devops4j on 2017/5/28.
 */
public interface Reflector {
    /**
     * 获取反射的目标类
     * @return 类对象
     */
    Class<?> getType();

    /**
     * 获取无参构造函数构造器
     * @return 函数构造器
     */
    Constructor<?> getDefaultConstructor();

    /**
     * 是否存在午餐
     * @return
     */
    boolean hasDefaultConstructor();

    /**
     * 根据属性名获取执行器
     * @param propertyName
     * @return
     */
    Invoker getSetInvoker(String propertyName);

    /**
     * 根据属性名获取执行器
     * @param propertyName
     * @return
     */
    Invoker getGetInvoker(String propertyName);

    /**
     * 根据属性名获取类对象
     * @param propertyName
     * @return
     */
    Class<?> getSetterType(String propertyName);

    /**
     * 根据属性名获取类对象
     * @param propertyName
     * @return
     */
    Class<?> getGetterType(String propertyName);

    /**
     * 获取可进行Get属性名数组
     * @return
     */
    String[] getGettablePropertyNames();

    /**
     * 获取可进行Set属性名数组
     * @return
     */
    String[] getSettablePropertyNames();

    /**
     * 根据属性名检测是否有Setter
     * @param propertyName
     * @return
     */
    boolean hasSetter(String propertyName);

    /**
     * 根据属性名检测是否有Getter
     * @param propertyName
     * @return
     */
    boolean hasGetter(String propertyName);
}
