package com.devops4j.reflection4j.wrapper;

import com.devops4j.reflection4j.*;
import com.devops4j.reflection4j.factory.MetaClassFactory;
import com.devops4j.reflection4j.factory.MetaObjectFactory;
import com.devops4j.reflection4j.meta.DefaultMetaObject;
import com.devops4j.reflection4j.property.PropertyTokenizer;
import com.devops4j.logtrace4j.ErrorContextFactory;

import java.util.Collection;
import java.util.List;

public class BeanWrapper extends BaseWrapper {

    private Object object;
    private MetaClass metaClass;
    MetaClassFactory metaClassFactory;
    MetaObjectFactory metaObjectFactory;

    public BeanWrapper(MetaObject metaObject, Object object, MetaClassFactory metaClassFactory,MetaObjectFactory metaObjectFactory) {
        super(metaObject);
        this.object = object;
        this.metaClassFactory = metaClassFactory;
        this.metaObjectFactory = metaObjectFactory;
        this.metaClass = metaClassFactory.forClass(object.getClass());
    }

    public Object get(PropertyTokenizer prop) {
        String index = prop.getIndex();
        if (index != null) {
            Object collection = resolveCollection(prop, object);
            return getCollectionValue(prop, collection);
        } else {
            return getBeanProperty(prop, object);
        }
    }

    Object getBeanProperty(PropertyTokenizer prop, Object object) {
        try {
            String property = prop.getName();
            Invoker method = metaClass.getGetter(property);
            try {
                return method.invoke(object, NO_ARGUMENTS);
            } catch (Throwable t) {
                throw t;
            }
        } catch (RuntimeException e) {
            ErrorContextFactory.instance().message("Could not get property '{}' from {}.", prop.getName(), object.getClass()).cause(e).throwError();
            return null;
        } catch (Throwable t) {
            ErrorContextFactory.instance().message("Could not get property '{}' from {}.", prop.getName(), object.getClass()).cause(t).throwError();
            return null;
        }
    }

    public void set(PropertyTokenizer prop, Object value) {
        if (prop.getIndex() != null) {
            Object collection = resolveCollection(prop, object);
            setCollectionValue(prop, collection, value);
        } else {
            setBeanProperty(prop, object, value);
        }
    }

    void setBeanProperty(PropertyTokenizer prop, Object object, Object value) {
        try {
            Invoker method = metaClass.getSetter(prop.getName());
            Object[] params = {value};
            try {
                method.invoke(object, params);
            } catch (Throwable t) {
                throw new RuntimeException(t);
            }
        } catch (Throwable t) {
            ErrorContextFactory.instance().message("Could not set property '{}' of '{}' with value '{}'.", prop.getName(), object.getClass(), value).cause(t).throwError();
            return;
        }
    }

    public String findProperty(String name, boolean useCamelCaseMapping) {
        return metaClass.findProperty(name, useCamelCaseMapping);
    }

    public Collection<String> getGetterNames() {
        return metaClass.getGetterNames();
    }

    public Collection<String> getSetterNames() {
        return metaClass.getSetterNames();
    }

    public Class<?> getSetterType(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
            if (metaValue == GlobalSystemMetadata.NULL_META_OBJECT) {
                return metaClass.getSetterType(name);
            } else {
                return metaValue.getSetterType(prop.getChildren());
            }
        } else {
            return metaClass.getSetterType(name);
        }
    }

    public Class<?> getGetterType(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
            if (metaValue == GlobalSystemMetadata.NULL_META_OBJECT) {
                return metaClass.getGetterType(name);
            } else {
                return metaValue.getGetterType(prop.getChildren());
            }
        } else {
            return metaClass.getGetterType(name);
        }
    }

    public boolean hasSetter(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            if (metaClass.hasSetter(prop.getIndexedName())) {
                MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
                if (metaValue == GlobalSystemMetadata.NULL_META_OBJECT) {
                    return metaClass.hasSetter(name);
                } else {
                    return metaValue.hasSetter(prop.getChildren());
                }
            } else {
                return false;
            }
        } else {
            return metaClass.hasSetter(name);
        }
    }

    public boolean hasGetter(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            if (metaClass.hasGetter(prop.getIndexedName())) {
                MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
                if (metaValue == GlobalSystemMetadata.NULL_META_OBJECT) {
                    return metaClass.hasGetter(name);
                } else {
                    return metaValue.hasGetter(prop.getChildren());
                }
            } else {
                return false;
            }
        } else {
            return metaClass.hasGetter(name);
        }
    }

    public boolean isCollection() {
        return false;
    }

    public void add(Object element) {
        throw new UnsupportedOperationException();
    }

    public <E> void addAll(List<E> list) {
        throw new UnsupportedOperationException();
    }

    public <T> T getNativeObject() {
        return (T) object;
    }

    public MetaObject instantiatePropertyValue(PropertyTokenizer prop, ObjectFactory objectFactory) {
        MetaObject metaValue;
        Class<?> type = getSetterType(prop.getName());
        try {
            Object newObject = objectFactory.create(type);
            metaValue = new DefaultMetaObject(newObject, metaObject.getObjectFactory(), metaObject.getObjectWrapperFactory(), metaObject.getReflectorFactory(), metaObject.getMetaClassFactory(), metaObject.getMetaObjectFactory());
            set(prop, newObject);
        } catch (Exception e) {
            ErrorContextFactory.instance().message("Cannot set value of property '{}' because '{}' is null and cannot be instantiated on instance of {}.", prop.getName(), prop.getName(), type.getName()).cause(e).throwError();
            return null;
        }
        return metaValue;
    }

}