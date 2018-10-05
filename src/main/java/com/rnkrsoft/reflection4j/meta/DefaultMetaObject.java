/**
 * RNKRSOFT OPEN SOURCE SOFTWARE LICENSE TERMS ver.1
 * - 氡氪网络科技(重庆)有限公司 开源软件许可条款(版本1)
 * 氡氪网络科技(重庆)有限公司 以下简称Rnkrsoft。
 * 这些许可条款是 Rnkrsoft Corporation（或您所在地的其中一个关联公司）与您之间达成的协议。
 * 请阅读本条款。本条款适用于所有Rnkrsoft的开源软件项目，任何个人或企业禁止以下行为：
 * .禁止基于删除开源代码所附带的本协议内容、
 * .以非Rnkrsoft的名义发布Rnkrsoft开源代码或者基于Rnkrsoft开源源代码的二次开发代码到任何公共仓库,
 * 除非上述条款附带有其他条款。如果确实附带其他条款，则附加条款应适用。
 * <p/>
 * 使用该软件，即表示您接受这些条款。如果您不接受这些条款，请不要使用该软件。
 * 如下所述，安装或使用该软件也表示您同意在验证、自动下载和安装某些更新期间传输某些标准计算机信息以便获取基于 Internet 的服务。
 * <p/>
 * 如果您遵守这些许可条款，将拥有以下权利。
 * 1.阅读源代码和文档
 * 如果您是个人用户，则可以在任何个人设备上阅读、分析、研究Rnkrsoft开源源代码。
 * 如果您经营一家企业，则可以在无数量限制的设备上阅读Rnkrsoft开源源代码,禁止分析、研究Rnkrsoft开源源代码。
 * 2.编译源代码
 * 如果您是个人用户，可以对Rnkrsoft开源源代码以及修改后产生的源代码进行编译操作，编译产生的文件依然受本协议约束。
 * 如果您经营一家企业，不可以对Rnkrsoft开源源代码以及修改后产生的源代码进行编译操作。
 * 3.二次开发拓展功能
 * 如果您是个人用户，可以基于Rnkrsoft开源源代码进行二次开发，修改产生的元代码同样受本协议约束。
 * 如果您经营一家企业，不可以对Rnkrsoft开源源代码进行任何二次开发，但是可以通过联系Rnkrsoft进行商业授予权进行修改源代码。
 * 完整协议。本协议以及开源源代码附加协议，共同构成了Rnkrsoft开源软件的完整协议。
 * <p/>
 * 4.免责声明
 * 该软件按“原样”授予许可。 使用本文档的风险由您自己承担。Rnkrsoft 不提供任何明示的担保、保证或条件。
 * 5.版权声明
 * 本协议所对应的软件为 Rnkrsoft 所拥有的自主知识版权，如果基于本软件进行二次开发，在不改变本软件的任何组成部分的情况下的而二次开发源代码所属版权为贵公司所有。
 */
package com.rnkrsoft.reflection4j.meta;

import com.rnkrsoft.reflection4j.*;
import com.rnkrsoft.reflection4j.factory.MetaClassFactory;
import com.rnkrsoft.reflection4j.factory.MetaObjectFactory;
import com.rnkrsoft.reflection4j.property.PropertyTokenizer;
import com.rnkrsoft.reflection4j.wrapper.BeanWrapper;
import com.rnkrsoft.reflection4j.wrapper.CollectionWrapper;
import com.rnkrsoft.reflection4j.wrapper.MapWrapper;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by rnkrsoft on 2017/6/19.
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

        if (ObjectWrapper.class.isAssignableFrom(type)) {
            this.objectWrapper = (ObjectWrapper) object;
        } else if (objectWrapperFactory.hasWrapperFor(object)) {
            this.objectWrapper = objectWrapperFactory.getWrapperFor(this, object);
        } else if (Map.class.isAssignableFrom(type)) {
            this.objectWrapper = new MapWrapper(type, this, (Map) object);
        } else if (Collection.class.isAssignableFrom(type)) {
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
