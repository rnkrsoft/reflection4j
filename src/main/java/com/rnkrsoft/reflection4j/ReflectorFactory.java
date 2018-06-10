package com.rnkrsoft.reflection4j;


public interface ReflectorFactory {

    boolean isClassCacheEnabled();

    void setClassCacheEnabled(boolean classCacheEnabled);

    Reflector reflector(Class<?> type);
}