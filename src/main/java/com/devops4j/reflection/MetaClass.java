package com.devops4j.reflection;

/**
 * Created by devops4j on 2017/5/28.
 * 类元信息
 */
public interface MetaClass {
    /**
     * 返回是否有Getter方法
     * @param fieldName 字段名
     * @return 如果存在返回真
     */
    boolean hasGetter(String fieldName);
    /**
     * 返回是否有Setter方法
     * @param fieldName 字段名
     * @return 如果存在返回真
     */
    boolean hasSetter(String fieldName);

    /**
     * 返回该类对应的所有Getter方法
     * @return Getter方法列表
     */
    String[] getGetterNames();
    /**
     * 返回该类对应的所有Setter方法
     * @return Setter方法列表
     */
    String[] getSetterNames();
    String findProperty(String name);
    /**
     * 查找属性名
     * @param name 属性名
     * @param useCamelCaseMapping 是否驼峰命名
     * @return
     */
    String findProperty(String name, boolean useCamelCaseMapping);
    /**
     * 根据字段名提取属性名称，可能返回很复杂的名称
     * @param property
     * @param builder
     * @return
     */
    StringBuilder buildProperty(String property, StringBuilder builder);
    /**
     * 获取Getter执行器
     * @param property 字段名
     * @return 执行器
     */
    Invoker getGetInvoker(String property);
    /**
     * 获取Setter执行器
     * @param property 字段名
     * @return 执行器
     */
    Invoker getSetInvoker(String property);

    /**
     * 获取Getter返回值类型
     * @param property 字段名
     * @return 类型
     */
    Class<?> getGetterType(String property);

    Class<?> getSetterType(String property);
    /**
     * 是否存在默认构造函数
     * @return 返回真
     */
    boolean hasDefaultConstructor();
}
