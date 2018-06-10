package com.rnkrsoft.reflection4j;


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
     * @param <T>
     * @return 执行结果
     * @throws Exception 异常
     */
    <T> T invoke(Object target, Object... args) throws Exception;

    /**
     * 执行包装的方法
     * @param target 目标对象
     * @param args 参数映射
     * @param <T>
     * @return 执行结果
     * @throws Exception 异常
     */
    <T> T invoke(Object target, Map<String, Object> args) throws Exception;

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

    /**
     * 入参类型
     * @return
     */
    Class<?>[] getParamIndexClass();
}
