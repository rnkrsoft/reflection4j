package com.devops4j.reflection;

import java.lang.reflect.InvocationTargetException;

/**
 * 执行器
 */
public interface Invoker {
    /**
     * 执行包装的方法
     * @param target 目标对象
     * @param args 参数数组
     * @return 执行结果
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    <T> T invoke(Object target, Object... args) throws IllegalAccessException, InvocationTargetException;

    /**
     * 方法返回类型
     * @return 类型
     */
    Class<?> getType();
}
