package com.rnkrsoft.reflection4j.factory;

import com.rnkrsoft.reflection4j.meta.DefaultMetaClass;
import com.rnkrsoft.reflection4j.MetaClass;
import com.rnkrsoft.reflection4j.ReflectorFactory;
import lombok.Getter;

/**
 * Created by rnkrsoft on 2017/7/12.
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
