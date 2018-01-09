package com.devops4j.reflection4j.factory;

import com.devops4j.reflection4j.reflector.DefaultReflector;
import com.devops4j.reflection4j.Reflector;
import com.devops4j.reflection4j.ReflectorFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DefaultReflectorFactory implements ReflectorFactory {
    private boolean classCacheEnabled = true;
    private final ConcurrentMap<Class<?>, Reflector> reflectorMap = new ConcurrentHashMap<Class<?>, Reflector>();

    public DefaultReflectorFactory() {
    }

    public boolean isClassCacheEnabled() {
        return classCacheEnabled;
    }

    public void setClassCacheEnabled(boolean classCacheEnabled) {
        this.classCacheEnabled = classCacheEnabled;
    }

    public Reflector reflector(Class<?> type) {
        if (classCacheEnabled) {
            // synchronized (type) removed see issue #461
            Reflector cached = reflectorMap.get(type);
            if (cached == null) {
                cached = new DefaultReflector(type);
                reflectorMap.put(type, cached);
            }
            return cached;
        } else {
            return new DefaultReflector(type);
        }
    }

}
