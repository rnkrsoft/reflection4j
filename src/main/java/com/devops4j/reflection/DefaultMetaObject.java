package com.devops4j.reflection;

import com.devops4j.reflection.factory.MetaObjectFactory;
import com.devops4j.reflection.property.PropertyTokenizer;
import com.devops4j.reflection.wrapper.BeanWrapper;
import com.devops4j.reflection.wrapper.CollectionWrapper;
import com.devops4j.reflection.wrapper.MapWrapper;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by devops4j on 2017/6/19.
 */
public class DefaultMetaObject implements MetaObject {
    @Getter
    Object originalObject;
    @Getter
    ObjectWrapper objectWrapper;
    @Getter
    ObjectFactory objectFactory;
    @Getter
    ObjectWrapperFactory objectWrapperFactory;
    @Getter
    ReflectorFactory reflectorFactory;

    public DefaultMetaObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        this.originalObject = object;
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;

        if (object instanceof ObjectWrapper) {
            this.objectWrapper = (ObjectWrapper) object;
        } else if (objectWrapperFactory.hasWrapperFor(object)) {
            this.objectWrapper = objectWrapperFactory.getWrapperFor(this, object);
        } else if (object instanceof Map) {
            this.objectWrapper = new MapWrapper(this, (Map) object);
        } else if (object instanceof Collection) {
            this.objectWrapper = new CollectionWrapper(this, (Collection) object);
        } else {
            this.objectWrapper = new BeanWrapper(this, object);
        }
    }

    public boolean hasGetter(String fieldName) {
        return objectWrapper.hasGetter(fieldName);
    }

    public boolean hasSetter(String fieldName) {
        return objectWrapper.hasSetter(fieldName);
    }

    public void setValue(String fieldName, Object value) {
        PropertyTokenizer prop = new PropertyTokenizer(fieldName);
        if (prop.hasNext()) {
            String indexedName = prop.getIndexedName();
            MetaObject metaValue = metaObjectForProperty(indexedName);
            if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
                if (value == null && prop.getChildren() != null) {
                    // don't instantiate child path if value is null
                    return;
                } else {
                    metaValue = objectWrapper.instantiatePropertyValue(fieldName, prop, objectFactory);
                }
            }
            metaValue.setValue(prop.getChildren(), value);
        } else {
            objectWrapper.set(prop, value);
        }
    }

    public <T> T getValue(String fieldName) {
        PropertyTokenizer prop = new PropertyTokenizer(fieldName);
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

    public Class<?> getSetterType(String fieldName) {
        return objectWrapper.getSetterType(fieldName);
    }

    public Class<?> getGetterType(String fieldName) {
        return objectWrapper.getGetterType(fieldName);
    }

    public String[] getGetterNames() {
        return objectWrapper.getGetterNames();
    }

    public String[] getSetterNames() {
        return objectWrapper.getSetterNames();
    }

    public String findProperty(String propName, boolean useCamelCaseMapping) {
        return objectWrapper.findProperty(propName, useCamelCaseMapping);
    }

    public boolean isCollection() {
        return objectWrapper.isCollection();
    }

    public MetaObject metaObjectForProperty(String name) {
        Object value = getValue(name);
        return MetaObjectFactory.forObject(value, objectFactory, objectWrapperFactory, reflectorFactory);
    }

    public <E> void addAll(List<E> list) {
        objectWrapper.addAll(list);
    }

    public void add(Object element) {
        objectWrapper.add(element);
    }
}
