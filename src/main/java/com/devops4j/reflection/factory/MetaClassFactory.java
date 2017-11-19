package com.devops4j.reflection.factory;

import com.devops4j.reflection.MetaClass;
import com.devops4j.reflection.DefaultMetaClass;
import com.devops4j.reflection.ReflectorFactory;

/**
 * Created by devops4j on 2017/7/12.
 */
public class MetaClassFactory {
    static ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();

    public static MetaClass forClass(Class<?> type){
        return new DefaultMetaClass(type, REFLECTOR_FACTORY);
    }
}
