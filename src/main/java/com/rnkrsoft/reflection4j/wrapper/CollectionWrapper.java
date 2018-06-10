package com.rnkrsoft.reflection4j.wrapper;

import com.rnkrsoft.reflection4j.MetaObject;
import com.rnkrsoft.reflection4j.ObjectFactory;
import com.rnkrsoft.reflection4j.ObjectWrapper;
import com.rnkrsoft.reflection4j.property.PropertyTokenizer;

import java.util.Collection;
import java.util.List;

public class CollectionWrapper extends  BaseWrapper implements ObjectWrapper {

    private Collection<Object> object;

    public CollectionWrapper(Class type, MetaObject metaObject, Collection<Object> object) {
        super(type, metaObject);
        this.object = object;
    }

    public Object get(PropertyTokenizer prop) {
        throw new UnsupportedOperationException();
    }

    public void set(PropertyTokenizer prop, Object value) {
        throw new UnsupportedOperationException();
    }

    public String findProperty(String name, boolean useCamelCaseMapping) {
        throw new UnsupportedOperationException();
    }

    public Collection getGetterNames() {
        throw new UnsupportedOperationException();
    }

    public Collection getSetterNames() {
        throw new UnsupportedOperationException();
    }

    public Class<?> getSetterType(String name) {
        throw new UnsupportedOperationException();
    }

    public Class<?> getGetterType(String name) {
        throw new UnsupportedOperationException();
    }

    public boolean hasSetter(String name) {
        throw new UnsupportedOperationException();
    }

    public boolean hasGetter(String name) {
        throw new UnsupportedOperationException();
    }

    public boolean isCollection() {
        return true;
    }

    public void add(Object element) {
        object.add(element);
    }

    public <E> void addAll(List<E> element) {
        object.addAll(element);
    }

    public <T> T getNativeObject() {
        return (T) object;
    }

    public MetaObject instantiatePropertyValue(PropertyTokenizer prop, ObjectFactory objectFactory) {
        throw new UnsupportedOperationException();
    }

}