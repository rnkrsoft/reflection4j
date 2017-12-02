package com.devops4j.reflection4j.wrapper;

import com.devops4j.reflection4j.MetaObject;
import com.devops4j.reflection4j.ObjectFactory;
import com.devops4j.reflection4j.ObjectWrapper;
import com.devops4j.reflection4j.property.PropertyTokenizer;

import java.util.Collection;
import java.util.List;

public class CollectionWrapper implements ObjectWrapper {

    private Collection<Object> object;

    public CollectionWrapper(MetaObject metaObject, Collection<Object> object) {
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

    public String[] getGetterNames() {
        throw new UnsupportedOperationException();
    }

    public String[] getSetterNames() {
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

    public MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory) {
        throw new UnsupportedOperationException();
    }

}