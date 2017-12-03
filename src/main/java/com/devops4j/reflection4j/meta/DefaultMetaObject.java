package com.devops4j.reflection4j.meta;

import com.devops4j.reflection4j.*;
import com.devops4j.reflection4j.factory.MetaClassFactory;
import com.devops4j.reflection4j.factory.MetaObjectFactory;
import com.devops4j.reflection4j.property.PropertyTokenizer;
import com.devops4j.reflection4j.wrapper.BeanWrapper;
import com.devops4j.reflection4j.wrapper.CollectionWrapper;
import com.devops4j.reflection4j.wrapper.MapWrapper;
import com.devops4j.logtrace4j.ErrorContextFactory;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by devops4j on 2017/6/19.
 */
public class DefaultMetaObject implements MetaObject {
    @Getter
    Object object;
    @Getter
    Class type;
    @Getter
    ObjectWrapper objectWrapper;
    @Getter
    ObjectFactory objectFactory;
    @Getter
    ObjectWrapperFactory objectWrapperFactory;
    @Getter
    ReflectorFactory reflectorFactory;
    @Getter
    MetaClassFactory metaClassFactory;
    @Getter
    MetaObjectFactory metaObjectFactory;

    public DefaultMetaObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory, MetaClassFactory metaClassFactory,MetaObjectFactory metaObjectFactory) {
        if (object == null) {
            ErrorContextFactory.instance().activity("创建对象元信息对象").message("对象为null,无法创建元信息对象").throwError();
            return;
        }
        this.object = object;
        this.type = this.object.getClass();
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;
        this.metaClassFactory = metaClassFactory;
        this.metaObjectFactory = metaObjectFactory;

        if (object instanceof ObjectWrapper) {
            this.objectWrapper = (ObjectWrapper) object;
        } else if (objectWrapperFactory.hasWrapperFor(object)) {
            this.objectWrapper = objectWrapperFactory.getWrapperFor(this, object);
        } else if (object instanceof Map) {
            this.objectWrapper = new MapWrapper(this, (Map) object);
        } else if (object instanceof Collection) {
            this.objectWrapper = new CollectionWrapper(this, (Collection) object);
        } else {
            this.objectWrapper = new BeanWrapper(this, object, metaClassFactory, metaObjectFactory);
        }
    }

    public boolean hasGetter(String propertyName) {
        return objectWrapper.hasGetter(propertyName);
    }

    public boolean hasSetter(String propertyName) {
        return objectWrapper.hasSetter(propertyName);
    }

    public void setValue(String propertyName, Object value) {
        PropertyTokenizer prop = new PropertyTokenizer(propertyName);
        if (prop.hasNext()) {
            String indexedName = prop.getIndexedName();
            MetaObject metaValue = metaObjectForProperty(indexedName);
            if (metaValue == GlobalSystemMetadata.NULL_META_OBJECT) {
                if (value == null && prop.getChildren() != null) {
                    // don't instantiate child path if value is null
                    return;
                } else {
                    metaValue = objectWrapper.instantiatePropertyValue(prop, objectFactory);
                }
            }
            metaValue.setValue(prop.getChildren(), value);
        } else {
            objectWrapper.set(prop, value);
        }
    }

    public <T> T getValue(String propertyName) {
        PropertyTokenizer prop = new PropertyTokenizer(propertyName);
        if (prop.hasNext()) {
            String indexedName = prop.getIndexedName();
            MetaObject metaValue = metaObjectForProperty(indexedName);
            if (metaValue == null) {
                return null;
            } else {
                return metaValue.getValue(prop.getChildren());
            }
        } else {
            return (T) objectWrapper.get(prop);
        }
    }

    public Class<?> getSetterType(String propertyName) {
        return objectWrapper.getSetterType(propertyName);
    }

    public Class<?> getGetterType(String propertyName) {
        return objectWrapper.getGetterType(propertyName);
    }

    public Collection<String> getGetterNames() {
        return objectWrapper.getGetterNames();
    }

    public Collection<String> getSetterNames() {
        return objectWrapper.getSetterNames();
    }

    public String findProperty(String propertyName, boolean useCamelCaseMapping) {
        return objectWrapper.findProperty(propertyName, useCamelCaseMapping);
    }

    public String findProperty(String propertyName) {
        return objectWrapper.findProperty(propertyName, false);
    }

    public boolean isCollection() {
        return objectWrapper.isCollection();
    }

    public MetaObject metaObjectForProperty(String propertyName) {
        PropertyTokenizer prop = new PropertyTokenizer(propertyName);
        if (prop.hasNext()) {
            String indexedName = prop.getIndexedName();
            MetaObject metaObject = metaObjectForProperty(indexedName);
            if (metaObject == GlobalSystemMetadata.NULL_META_OBJECT) {
                return metaObject;
            } else {
                return metaObject.metaObjectForProperty(prop.getChildren());
            }
        } else {
            Object object = objectWrapper.get(prop);
            return metaObjectFactory.forObject(object);
        }
    }

    public MetaClass metaClassForProperty(String propertyName) {
        return getMetaClass().metaClassForProperty(propertyName);
    }

    public MetaClass getMetaClass() {
        MetaClass metaClass = metaClassFactory.forClass(type);
        return metaClass;
    }


    public <E> void addAll(List<E> list) {
        objectWrapper.addAll(list);
    }

    public void add(Object element) {
        objectWrapper.add(element);
    }

    public MetaObject instantiatePropertyValue(String propertyName) {
        PropertyTokenizer prop = new PropertyTokenizer(propertyName);
        if (prop.hasNext()) {
            String indexedName = prop.getIndexedName();
            MetaObject metaObject = metaObjectForProperty(indexedName);
            if (metaObject == GlobalSystemMetadata.NULL_META_OBJECT) {
                return objectWrapper.instantiatePropertyValue(prop, objectFactory);
            } else {
                return metaObject.metaObjectForProperty(prop.getChildren());
            }
        } else {
            Object object = objectWrapper.get(prop);
            return metaObjectFactory.forObject(object);
        }
    }
}
