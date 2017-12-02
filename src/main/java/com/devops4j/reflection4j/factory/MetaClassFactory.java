package com.devops4j.reflection4j.factory;

import com.devops4j.reflection4j.meta.DefaultMetaClass;
import com.devops4j.reflection4j.MetaClass;
import com.devops4j.reflection4j.ReflectorFactory;

/**
 * Created by devops4j on 2017/7/12.
 */
public class MetaClassFactory {
    final static ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();

    public static MetaClass forClass(Class<?> type) {
        return new DefaultMetaClass(type, REFLECTOR_FACTORY);
    }
}
