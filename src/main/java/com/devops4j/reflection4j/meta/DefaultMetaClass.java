package com.devops4j.reflection4j.meta;

import com.devops4j.reflection4j.*;
import com.devops4j.reflection4j.factory.MetaClassFactory;
import com.devops4j.reflection4j.invoker.GetFieldInvoker;
import com.devops4j.reflection4j.invoker.MethodInvoker;
import com.devops4j.reflection4j.property.PropertyTokenizer;
import com.devops4j.track.ErrorContextFactory;

import java.lang.reflect.*;
import java.util.Collection;

/**
 * Created by devops4j on 2017/6/19.
 * 默认实现的类元信息
 */
public class DefaultMetaClass implements MetaClass {
    ReflectorFactory reflectorFactory;
    Reflector reflector;
    MetaClassFactory metaClassFactory;

    public DefaultMetaClass(Class<?> type, ReflectorFactory reflectorFactory, MetaClassFactory metaClassFactory) {
        this.reflectorFactory = reflectorFactory;
        this.reflector = reflectorFactory.findForClass(type);
        this.metaClassFactory = metaClassFactory;
    }
    MetaClass metaClassForProperty(PropertyTokenizer prop){
        if (prop.hasNext()) {
            Class clazz = reflector.getGetterType(prop.getName());
            return metaClassFactory.forClass(clazz).metaClassForProperty(prop.getChildren());
        }else{
            String propertyName = prop.getName();
            Class<?> propType = reflector.getGetterType(propertyName);
            return metaClassFactory.forClass(propType);
        }
    }
    public MetaClass metaClassForProperty(String propertyName) {
        PropertyTokenizer prop = new PropertyTokenizer(propertyName);
        return metaClassForProperty(prop);
    }

    public boolean hasGetter(String propertyName) {
        PropertyTokenizer prop = new PropertyTokenizer(propertyName);
        if (prop.hasNext()) {
            if (reflector.hasSetter(prop.getName())) {
                MetaClass metaProp = metaClassForProperty(prop.getName());
                return metaProp.hasSetter(prop.getChildren());
            } else {
                return false;
            }
        } else {
            return reflector.hasSetter(prop.getName());
        }
    }

    public boolean hasSetter(String propertyName) {
        PropertyTokenizer prop = new PropertyTokenizer(propertyName);
        if (prop.hasNext()) {
            if (reflector.hasGetter(prop.getName())) {
                MetaClass metaProp = metaClassForProperty(prop);
                return metaProp.hasGetter(prop.getChildren());
            } else {
                return false;
            }
        } else {
            return reflector.hasGetter(prop.getName());
        }
    }

    public Collection<String> getGetterNames() {
        return reflector.getGettablePropertyNames();
    }

    public Collection<String> getSetterNames() {
        return reflector.getSettablePropertyNames();
    }

    public String findProperty(String propertyName) {
        StringBuilder prop = buildProperty(propertyName, new StringBuilder());
        return prop.length() > 0 ? prop.toString() : null;
    }

    public String findProperty(String propertyName, boolean useCamelCaseMapping) {
        if (useCamelCaseMapping) {
            propertyName = propertyName.replace("_", "");
        }
        return findProperty(propertyName);
    }

    public Invoker getGetter(String propertyName) {
        return reflector.getGetter(propertyName);
    }

    public Invoker getSetter(String propertyName) {
        return reflector.getSetter(propertyName);
    }

    public Class<?> getGetterType(String propertyName) {
        PropertyTokenizer prop = new PropertyTokenizer(propertyName);
        if (prop.hasNext()) {
            MetaClass metaProp = metaClassForProperty(prop);
            return metaProp.getGetterType(prop.getChildren());
        }
        // issue #506. Resolve the type inside a Collection Object
        return getGetterType(prop);
    }

    public Class<?> getSetterType(String propertyName) {
        PropertyTokenizer prop = new PropertyTokenizer(propertyName);
        if (prop.hasNext()) {
            MetaClass metaProp = metaClassForProperty(prop.getName());
            return metaProp.getSetterType(prop.getChildren());
        } else {
            return reflector.getSetterType(prop.getName());
        }
    }

    public Class<?> getSetterType(PropertyTokenizer prop) {
        Class<?> type = reflector.getGetterType(prop.getName());
        if (prop.getIndex() != null && Collection.class.isAssignableFrom(type)) {
            Type returnType = getGenericGetterType(prop.getName());
            if (returnType instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                    returnType = actualTypeArguments[0];
                    if (returnType instanceof Class) {
                        type = (Class<?>) returnType;
                    } else if (returnType instanceof ParameterizedType) {
                        type = (Class<?>) ((ParameterizedType) returnType).getRawType();
                    }
                }
            }
        }
        return type;
    }

    public boolean hasDefaultConstructor() {
        return reflector.hasDefaultConstructor();
    }

    public <T> T newInstance() {
        ErrorContextFactory.instance().activity("创建实例");
        try {
            return (T) reflector.getDefaultConstructor().newInstance();
        } catch (InstantiationException e) {
            ErrorContextFactory.instance().message("构建实例发生错误,因为该类为不能实例化的类").solution("修改类'{}'为可实现的类,不能为接口,抽象类", reflector.getType()).cause(e).throwError();
            return null;
        } catch (IllegalAccessException e) {
            ErrorContextFactory.instance().message("构建实例发生错误,因为该类的构造方法不能访问").solution("修改类'{}'有public的无参构造函数", reflector.getType()).cause(e).throwError();
            return null;
        } catch (InvocationTargetException e) {
            ErrorContextFactory.instance().message("构建实例发生错误,因为该类的无参构造中抛出了异常").solution("检查类'{}'的public无参构造函数中构造条件", reflector.getType()).cause(e.getTargetException()).throwError();
            return null;
        } finally {
            ErrorContextFactory.instance().activity(null);
        }
    }

    public Class getType() {
        return reflector.getType();
    }

    public Reflector getReflector() {
        return reflector;
    }




    private Class<?> getGetterType(PropertyTokenizer prop) {
        Class<?> type = reflector.getGetterType(prop.getName());
        if (prop.getIndex() != null && Collection.class.isAssignableFrom(type)) {
            Type returnType = getGenericGetterType(prop.getName());
            if (returnType instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length == 1) {
                    returnType = actualTypeArguments[0];
                    if (returnType instanceof Class) {
                        type = (Class<?>) returnType;
                    } else if (returnType instanceof ParameterizedType) {
                        type = (Class<?>) ((ParameterizedType) returnType).getRawType();
                    }
                }
            }
        }
        return type;
    }

    private Type getGenericGetterType(String propertyName) {
        try {
            Invoker invoker = reflector.getGetter(propertyName);
            if (invoker instanceof MethodInvoker) {
                Field _method = MethodInvoker.class.getDeclaredField("method");
                _method.setAccessible(true);
                Method method = (Method) _method.get(invoker);
                return method.getGenericReturnType();
            } else if (invoker instanceof GetFieldInvoker) {
                Field _field = GetFieldInvoker.class.getDeclaredField("field");
                _field.setAccessible(true);
                Field field = (Field) _field.get(invoker);
                return field.getGenericType();
            }
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        return null;
    }

    public StringBuilder buildProperty(String propertyName, StringBuilder builder) {
        ErrorContextFactory.instance().activity("检测属性");
        PropertyTokenizer prop = new PropertyTokenizer(propertyName);
        if (prop.hasNext()) {
            String propertyName0 = Utils.toCamelCase(prop.getName());
            if (propertyName != null) {
                builder.append(propertyName0);
                builder.append(".");
                //获取当前属性对象的类元信息
                MetaClass metaProp = metaClassForProperty(propertyName0);
                metaProp.buildProperty(prop.getChildren(), builder);
            }
        } else {//如果属性到最后一个对象，直接驼峰命名
            String propertyName0 = Utils.toCamelCase(propertyName);
            if (propertyName != null) {
                if (reflector.hasProperty(propertyName0)) {
                    builder.append(propertyName0);
                } else {
                    ErrorContextFactory.instance().message("类'{}'不存在属性'{}'", reflector.getType(), propertyName).throwError();
                    return null;
                }
            }

        }
        return builder;
    }

    public Invoker getMethodInvoker(String name, Class... classes) {
        return reflector.getMethodInvoker(name, classes);
    }
}
