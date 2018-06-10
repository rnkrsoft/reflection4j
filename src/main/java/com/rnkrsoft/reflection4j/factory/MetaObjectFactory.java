package com.rnkrsoft.reflection4j.factory;

import com.rnkrsoft.reflection4j.MetaObject;
import com.rnkrsoft.reflection4j.ObjectFactory;
import com.rnkrsoft.reflection4j.ObjectWrapperFactory;
import com.rnkrsoft.reflection4j.ReflectorFactory;
import com.rnkrsoft.reflection4j.meta.DefaultMetaObject;
import lombok.Getter;

/**
 * Created by rnkrsoft on 2017/7/12.
 */
public class MetaObjectFactory {
    @Getter
    ObjectFactory objectFactory;
    @Getter
    ObjectWrapperFactory objectWrapperFactory;
    @Getter
    ReflectorFactory reflectorFactory;
    @Getter
    MetaClassFactory metaClassFactory;

    public MetaObjectFactory(ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory, MetaClassFactory metaClassFactory) {
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;
        this.metaClassFactory = metaClassFactory;
    }

    public MetaObject forObject(String propertyName, Class type, Object object) {
        return new DefaultMetaObject(propertyName, type, object, objectFactory, objectWrapperFactory, reflectorFactory, metaClassFactory, this);
    }
}
