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

import com.rnkrsoft.logtrace4j.ErrorContextFactory;
import com.rnkrsoft.message.MessageFormatter;
import com.rnkrsoft.reflection4j.*;
import com.rnkrsoft.reflection4j.factory.MetaClassFactory;
import com.rnkrsoft.reflection4j.factory.MetaObjectFactory;
import com.rnkrsoft.reflection4j.invoker.GetFieldInvoker;
import com.rnkrsoft.reflection4j.invoker.MethodInvoker;
import com.rnkrsoft.reflection4j.property.PropertyTokenizer;
import com.rnkrsoft.utils.StringUtils;

import java.lang.reflect.*;
import java.util.Collection;

/**
 * Created by rnkrsoft on 2017/6/19.
 * 默认实现的类元信息
 */
public class DefaultMetaClass implements MetaClass {
    Reflector reflector;
    ObjectFactory objectFactory;
    ObjectWrapperFactory objectWrapperFactory;
    ReflectorFactory reflectorFactory;
    MetaClassFactory metaClassFactory;

    public DefaultMetaClass(Class<?> type, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory, MetaClassFactory metaClassFactory) {
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;
        this.reflector = reflectorFactory.reflector(type);
        this.metaClassFactory = metaClassFactory;
    }

    MetaClass metaClassForProperty(PropertyTokenizer prop) {
        if (prop.hasNext()) {
            Class clazz = reflector.getGetterType(prop.getName());
            return metaClassFactory.forClass(clazz).metaClassForProperty(prop.getChildren());
        } else {
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
            throw ErrorContextFactory.instance()
                    .message("构建实例发生错误,因为该类为不能实例化的类")
                    .solution("修改类'{}'为可实现的类,不能为接口,抽象类", reflector.getType())
                    .cause(e)
                    .runtimeException();
        } catch (IllegalAccessException e) {
            throw ErrorContextFactory.instance()
                    .message("构建实例发生错误,因为该类的构造方法不能访问")
                    .solution("修改类'{}'有public的无参构造函数", reflector.getType())
                    .cause(e)
                    .runtimeException();
        } catch (InvocationTargetException e) {
            throw ErrorContextFactory.instance()
                    .message("构建实例发生错误,因为该类的无参构造中抛出了异常")
                    .solution("检查类'{}'的public无参构造函数中构造条件", reflector.getType())
                    .cause(e.getTargetException())
                    .runtimeException();
        } finally {
            ErrorContextFactory.instance().activity(null);
        }
    }

    @Override
    public <T> T newInstance(Object... args) {
        Class[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg == null) {
                throw ErrorContextFactory.instance()
                        .message("class '{}' Constructor arg '{}' value is null", reflector.getType(), (i + 1))
                        .solution("please use No Argument Constructor")
                        .runtimeException();
            }
            parameterTypes[i] = arg.getClass();
        }
        Constructor constructor = reflector.getConstructor(parameterTypes);

        ErrorContextFactory.instance().activity("创建实例");
        try {
            return (T) constructor.newInstance(args);
        } catch (InstantiationException e) {
            throw ErrorContextFactory.instance()
                    .message("构建实例发生错误,因为该类为不能实例化的类")
                    .solution("修改类'{}'为可实现的类,不能为接口,抽象类", reflector.getType())
                    .cause(e)
                    .runtimeException();
        } catch (IllegalAccessException e) {
            throw ErrorContextFactory.instance()
                    .message("构建实例发生错误,因为该类的构造方法不能访问")
                    .solution("修改类'{}'有public的无参构造函数", reflector.getType())
                    .cause(e)
                    .runtimeException();
        } catch (InvocationTargetException e) {
            throw ErrorContextFactory.instance()
                    .message("构建实例发生错误,因为该类的无参构造中抛出了异常")
                    .solution("检查类'{}'的public无参构造函数中构造条件", reflector.getType())
                    .cause(e.getTargetException())
                    .runtimeException();
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

    @Override
    public MetaObject getMetaObject(Object object) {
        if (object == null) {
            throw new NullPointerException("object is null");
        }
        if (reflector.getType() != object.getClass()) {
            throw new ClassCastException(MessageFormatter.format("object is '{}' Class, but MetaClass is '{}'", object.getClass(), reflector.getType()));
        }
        MetaObjectFactory metaObjectFactory = new MetaObjectFactory(objectFactory, objectWrapperFactory, metaClassFactory.getReflectorFactory(), metaClassFactory);
        return metaObjectFactory.forObject(reflector.getType(), object);
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
            String propertyName0 = StringUtils.underlineToCamel(prop.getName());
            if (propertyName != null) {
                builder.append(propertyName0);
                builder.append(".");
                //获取当前属性对象的类元信息
                MetaClass metaProp = metaClassForProperty(propertyName0);
                metaProp.buildProperty(prop.getChildren(), builder);
            }
        } else {//如果属性到最后一个对象，直接驼峰命名
            String propertyName0 = StringUtils.underlineToCamel(propertyName);
            if (propertyName != null) {
                if (reflector.hasProperty(propertyName0)) {
                    builder.append(propertyName0);
                } else {
                    throw ErrorContextFactory.instance().message("类'{}'不存在属性'{}'", reflector.getType(), propertyName).runtimeException();
                }
            }

        }
        return builder;
    }

    public Invoker getMethodInvoker(String name, Class... classes) {
        return reflector.getMethodInvoker(name, classes);
    }
}
