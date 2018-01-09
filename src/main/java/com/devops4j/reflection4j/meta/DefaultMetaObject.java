package com.devops4j.reflection4j.meta;

import com.devops4j.reflection4j.*;
import com.devops4j.reflection4j.factory.MetaClassFactory;
import com.devops4j.reflection4j.factory.MetaObjectFactory;
import com.devops4j.reflection4j.property.PropertyTokenizer;
import com.devops4j.reflection4j.wrapper.BeanWrapper;
import com.devops4j.reflection4j.wrapper.CollectionWrapper;
import com.devops4j.reflection4j.wrapper.MapWrapper;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by devops4j on 2017/6/19.
 */
public class DefaultMetaObject implements MetaObject {
    @Getter
    String fullName;
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

    public DefaultMetaObject(String fullName, Class type, Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory, MetaClassFactory metaClassFactory, MetaObjectFactory metaObjectFactory) {
        this.fullName = fullName;
        this.object = object;
        this.type = type;
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;
        this.metaClassFactory = metaClassFactory;
        this.metaObjectFactory = metaObjectFactory;

        if (type.isAssignableFrom(ObjectWrapper.class)) {
            this.objectWrapper = (ObjectWrapper) object;
        } else if (objectWrapperFactory.hasWrapperFor(object)) {
            this.objectWrapper = objectWrapperFactory.getWrapperFor(this, object);
        } else if (type.isAssignableFrom(Map.class)) {
            this.objectWrapper = new MapWrapper(type, this, (Map) object);
        } else if (type.isAssignableFrom(Collection.class)) {
            this.objectWrapper = new CollectionWrapper(type, this, (Collection) object);
        } else {
            this.objectWrapper = new BeanWrapper(type, this, object, metaClassFactory, metaObjectFactory);
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
            if (metaValue.getObject() == null) {
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

    public MetaObject metaObjectForProperty(String propertyName) {
        return metaObjectForProperty(new ArrayList(), propertyName);
    }

    MetaObject metaObjectForProperty(List<String> parents, String propertyName) {
        PropertyTokenizer prop = new PropertyTokenizer(propertyName);
        if (prop.hasNext()) {
            String indexedName = prop.getIndexedName();
            DefaultMetaObject metaObject = (DefaultMetaObject) metaObjectForProperty(parents, indexedName);
            if (metaObject.getObject() == null) {
                return metaObject;
            } else {
                return metaObject.metaObjectForProperty(parents, prop.getChildren());
            }
        } else {
            parents.add(prop.getName());
            Object object = objectWrapper.get(prop);
            Class type0 = objectWrapper.getSetterType(prop.getName());
            if (type0 == null) {
                type0 = Object.class;
            }
            return metaObjectFactory.forObject(convertPathName(parents), type0, object);
        }
    }

    String convertPathName(List<String> parents) {
        StringBuilder path = new StringBuilder();
        for (int i = 0; i < parents.size(); i++) {
            String name = parents.get(i);
            if (i != 0) {
                path.append(".");
            }
            path.append(name);
        }
        return path.toString();
    }

    public MetaObject instantiatePropertyValue(String propertyName) {
        return instantiatePropertyValue(new ArrayList(), propertyName);
    }

    MetaObject instantiatePropertyValue(List<String> parents, String propertyName) {
        PropertyTokenizer prop = new PropertyTokenizer(propertyName);
        if (prop.hasNext()) {
            String indexedName = prop.getIndexedName();
            DefaultMetaObject metaObject = (DefaultMetaObject) metaObjectForProperty(parents, indexedName);
            if (metaObject.getObject() == null) {
                return objectWrapper.instantiatePropertyValue(prop, objectFactory);
            } else {
                return metaObject.metaObjectForProperty(parents, prop.getChildren());
            }
        } else {
            parents.add(prop.getName());
            Object object = objectWrapper.get(prop);
            Class type0 = objectWrapper.getSetterType(prop.getName());
            if (type0 == null) {
                type0 = Object.class;
            }
            return metaObjectFactory.forObject(convertPathName(parents), type0, object);
        }
    }
}
