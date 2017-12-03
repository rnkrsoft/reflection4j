package com.devops4j.reflection4j.wrapper;

import com.devops4j.reflection4j.meta.DefaultMetaObject;
import com.devops4j.reflection4j.MetaObject;
import com.devops4j.reflection4j.ObjectFactory;
import com.devops4j.reflection4j.property.PropertyTokenizer;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapWrapper extends BaseWrapper {

    private Map<String, Object> map;

    public MapWrapper(MetaObject metaObject, Map<String, Object> map) {
        super(metaObject);
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
        PropertyTokenizer prop = new PropertyTokenizer(name);
//    if (prop.hasNext()) {
//      MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
//      if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
//        return Object.class;
//      } else {
//        return metaValue.getSetterType(prop.getChildren());
//      }
//    } else {
//      if (map.get(name) != null) {
//        return map.get(name).getClass();
//      } else {
//        return Object.class;
//      }
//    }
        return null;
    }

    public Class<?> getGetterType(String name) {
//    PropertyTokenizer prop = new PropertyTokenizer(name);
//    if (prop.hasNext()) {
//      MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
//      if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
//        return Object.class;
//      } else {
//        return metaValue.getGetterType(prop.getChildren());
//      }
//    } else {
//      if (map.get(name) != null) {
//        return map.get(name).getClass();
//      } else {
//        return Object.class;
//      }
//    }
        return null;
    }

    public boolean hasSetter(String name) {
        return true;
    }

    public boolean hasGetter(String name) {
//    PropertyTokenizer prop = new PropertyTokenizer(name);
//    if (prop.hasNext()) {
//      if (map.containsKey(prop.getIndexedName())) {
//        MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
//        if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
//          return true;
//        } else {
//          return metaValue.hasGetter(prop.getChildren());
//        }
//      } else {
//        return false;
//      }
//    } else {
//      return map.containsKey(prop.getName());
//    }
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
        set(prop, map);
        return new DefaultMetaObject(map, metaObject.getObjectFactory(), metaObject.getObjectWrapperFactory(), metaObject.getReflectorFactory(), metaObject.getMetaClassFactory(), metaObject.getMetaObjectFactory());
    }

}