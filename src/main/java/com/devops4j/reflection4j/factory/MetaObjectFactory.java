package com.devops4j.reflection4j.factory;

import com.devops4j.reflection4j.*;
import com.devops4j.reflection4j.meta.DefaultMetaObject;
import lombok.Getter;

/**
 * Created by devops4j on 2017/7/12.
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

    public MetaObject forObject(Object object) {
        if (object == null) {
            return GlobalSystemMetadata.NULL_META_OBJECT;
        }else {
            return new DefaultMetaObject(object, objectFactory, objectWrapperFactory, reflectorFactory, metaClassFactory, this);
        }
    }
}
