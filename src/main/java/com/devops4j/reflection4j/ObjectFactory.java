package com.devops4j.reflection4j;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public interface ObjectFactory {
    /**
     * 注册接口和实现类
     * @param interfaceClass 接口类
     * @param implementClass 实现类
     */
    void register(Class interfaceClass, Class implementClass);

    /**
     * 获取接口和实现类的映射关系表
     * @return
     */
    Map<Class, Class> getMappings();

    void setProperties(Properties properties);

    /**
     * 创建实例
     * @param type 类型
     * @param <T> 实例
     * @return 实例
     */
    <T> T create(Class<T> type);

    /**
     * 创建实例
     * @param type 类型
     * @param constructorArgTypes 参数类型
     * @param constructorArgs 参数值
     * @param <T> 实例
     * @return 实例
     */
    <T> T create(Class<T> type, List<Class> constructorArgTypes, List<Object> constructorArgs);
    /**
     * 创建实例
     * @param type 类型
     * @param constructorArgTypes 参数类型
     * @param constructorArgs 参数值
     * @param <T> 实例
     * @return 实例
     */
    <T> T create(Class<T> type, Class[] constructorArgTypes, Object[] constructorArgs);

    /**
     * 是否为集合
     * @param type 类型
     * @param <T>
     * @return
     */
    <T> boolean isCollection(Class<T> type);

}