package com.devops4j.reflection4j.factory;

import com.devops4j.reflection4j.meta.DefaultMetaClass;
import com.devops4j.reflection4j.MetaClass;
import com.devops4j.reflection4j.ReflectorFactory;
import lombok.Getter;

/**
 * Created by devops4j on 2017/7/12.
 */
public class MetaClassFactory {
    @Getter
    ReflectorFactory reflectorFactory;

    public MetaClassFactory(ReflectorFactory reflectorFactory) {
        this.reflectorFactory = reflectorFactory;
    }

    public MetaClass forClass(Class<?> type) {
        return new DefaultMetaClass(type, reflectorFactory, this);
    }
}
