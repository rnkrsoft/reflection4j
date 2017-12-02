package com.devops4j.reflection4j;


import java.util.Map;

/**
 * 执行器
 */
public interface Invoker {
    /**
     * 执行包装的方法
     *
     * @param target 目标对象
     * @param args   参数数组
     * @return 执行结果
     * @throws IllegalAccessException
     * @throws Exception
     */
    <T> T invoke(Object target, Object... args) throws Throwable;
    <T> T invoke(Object target, Map<String, Object> args) throws Throwable;

    /**
     * 方法返回类型
     *
     * @return 类型
     */
    Class<?> getReturnClass();

    /**
     * 方法入参类型
     * @return
     */
    Map<String, Class<?>> getParamNameClass();
    Class<?>[] getParamIndexClass();
}
