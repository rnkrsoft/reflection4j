package com.rnkrsoft.reflection4j.wrapper;

import com.rnkrsoft.reflection4j.MetaObject;
import com.rnkrsoft.reflection4j.ObjectFactory;
import com.rnkrsoft.reflection4j.meta.DefaultMetaObject;
import com.rnkrsoft.reflection4j.property.PropertyTokenizer;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapWrapper extends BaseWrapper {

    private Map<String, Object> map;

    public MapWrapper(Class type, MetaObject metaObject, Map<String, Object> map) {
        super(type, metaObject);
        this.map = map;
    }

    public Object get(PropertyTokenizer prop) {
        if (prop.getIndex() != null) {
            Object collection = resolveCollection(prop, map);
            return getCollectionValue(prop, collection);
        } else {
            return map.get(prop.getName());
        }
    }

    public void set(PropertyTokenizer prop, Object value) {
        if (prop.getIndex() != null) {
            Object collection = resolveCollection(prop, map);
            setCollectionValue(prop, collection, value);
        } else {
            map.put(prop.getName(), value);
        }
    }

    public String findProperty(String name, boolean useCamelCaseMapping) {
        return name;
    }

    public Collection<String> getGetterNames() {
        return map.keySet();
    }

    public Collection<String> getSetterNames() {
        return map.keySet();
    }

    public Class<?> getSetterType(String name) {
        return null;
    }

    public Class<?> getGetterType(String name) {
        return null;
    }

    public boolean hasSetter(String name) {
        return false;
    }

    public boolean hasGetter(String name) {
        return false;
    }

    public boolean isCollection() {
        return false;
    }

    public void add(Object element) {
        throw new UnsupportedOperationException();
    }

    public <E> void addAll(List<E> element) {
        throw new UnsupportedOperationException();
    }

    public <T> Object getNativeObject() {
        return map;
    }

    public MetaObject instantiatePropertyValue(PropertyTokenizer prop, ObjectFactory objectFactory) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        String fullName = metaObject.getFullName() + "." + prop.getName();
        set(prop, map);
        return new DefaultMetaObject(fullName, map.getClass(), map, metaObject.getObjectFactory(), metaObject.getObjectWrapperFactory(), metaObject.getReflectorFactory(), metaObject.getMetaClassFactory(), metaObject.getMetaObjectFactory());
    }

}