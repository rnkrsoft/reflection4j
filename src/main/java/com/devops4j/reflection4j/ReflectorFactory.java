package com.devops4j.reflection4j;


public interface ReflectorFactory {

    boolean isClassCacheEnabled();

    void setClassCacheEnabled(boolean classCacheEnabled);

    Reflector reflector(Class<?> type);
}