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
package com.rnkrsoft.reflection4j.factory;

import com.rnkrsoft.logtrace4j.ErrorContextFactory;
import com.rnkrsoft.logtrace4j.TraceableRuntimeException;
import com.rnkrsoft.reflection4j.ObjectFactory;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultObjectFactory implements ObjectFactory, Serializable {

    final Map<Class, Class> registers = new ConcurrentHashMap<Class, Class>();

    public DefaultObjectFactory() {
        register(List.class, ArrayList.class);
        register(Collection.class, ArrayList.class);
        register(Iterable.class, ArrayList.class);
        register(Map.class, HashMap.class);
        register(SortedSet.class, TreeSet.class);
        register(Set.class, HashSet.class);
    }

    public <T> T create(Class<T> type) {
        Class<?> classToCreate = resolveInterface(type);
        return (T) instantiateClass(classToCreate, null, null);
    }

    public <T> T create(Class<T> type, List<Class> constructorArgTypes, List<Object> constructorArgs) {
        Class<?> classToCreate = resolveInterface(type);
        return (T) instantiateClass(classToCreate, constructorArgTypes, constructorArgs);
    }

    public <T> T create(Class<T> type, Class[] constructorArgTypes, Object[] constructorArgs) {
        Class<?> classToCreate = resolveInterface(type);
        return (T) instantiateClass(classToCreate, Arrays.asList(constructorArgTypes), Arrays.asList(constructorArgs));
    }

    public void register(Class interfaceClass, Class implementClass) {
        if (!interfaceClass.isInterface()) {
            throw ErrorContextFactory.instance().message("注册接口映射时,interfaceClass参数'{}'不是接口", interfaceClass).runtimeException();
        }
        if (implementClass.isInterface()) {
            throw ErrorContextFactory.instance().message("注册接口映射时,implementClass参数'{}'是接口", interfaceClass).solution("使用'{}'接口的实现类", interfaceClass).runtimeException();
        }
        if (implementClass.getModifiers() == Modifier.ABSTRACT) {
            throw ErrorContextFactory.instance().message("注册接口映射时,implementClass参数'{}'是抽象的", interfaceClass).solution("使用'{}'接口的非抽象实现类", interfaceClass).runtimeException();
        }
        registers.put(interfaceClass, implementClass);
    }

    public Map<Class, Class> getMappings() {
        return new HashMap(registers);
    }

    public void setProperties(Properties properties) {
        // no props for default
    }


    /**
     * 实例化类
     *
     * @param type
     * @param constructorArgTypes
     * @param constructorArgs
     * @param <T>
     * @return
     */
    <T> T instantiateClass(Class<T> type, List<Class> constructorArgTypes, List<Object> constructorArgs) {
        if (type.isArray()) {
            Class arrayClass = type.getComponentType();
            return (T) Array.newInstance(arrayClass, 0);
        }
        try {
            Constructor<T> constructor;
            //如果参数值或者参数类型为空，则采用默认构造函数进行创建
            if (constructorArgTypes == null || constructorArgs == null) {
                try {
                    constructor = type.getDeclaredConstructor();
                    //无参构造如果为私有，设置为可以访问
                    if (!constructor.isAccessible()) {
                        constructor.setAccessible(true);
                    }
                    //通过无参构造创建实例
                    return constructor.newInstance();
                } catch (Exception e) {
                    throw ErrorContextFactory.instance().message("类'{}'不能使用无参构造方法创建实例", type).solution("请换成该类的有参构造方法创建实例").cause(e).runtimeException();
                }
            }
            //如果参数值或者参数类型不为空，则采用带有参数的构造函数
            constructor = type.getDeclaredConstructor(constructorArgTypes.toArray(new Class[constructorArgTypes.size()]));
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance(constructorArgs.toArray(new Object[constructorArgs.size()]));
        } catch (TraceableRuntimeException e) {
            throw e;
        } catch (Exception e) {
            StringBuilder argTypes = new StringBuilder();
            if (constructorArgTypes != null && !constructorArgTypes.isEmpty()) {
                for (Class<?> argType : constructorArgTypes) {
                    argTypes.append(argType.getSimpleName());
                    argTypes.append(",");
                }
                argTypes.deleteCharAt(argTypes.length() - 1); // remove trailing ,
            }
            StringBuilder argValues = new StringBuilder();
            if (constructorArgs != null && !constructorArgs.isEmpty()) {
                for (Object argValue : constructorArgs) {
                    argValues.append(String.valueOf(argValue));
                    argValues.append(",");
                }
                argValues.deleteCharAt(argValues.length() - 1); // remove trailing ,
            }
            throw ErrorContextFactory.instance().message("Error instantiating {} with invalid types {} or values {}", type, argTypes, argValues).cause(e).runtimeException();
        }
    }

    /**
     * 根据对象接口连接到指定的实现类
     *
     * @param type
     * @return
     */
    protected Class<?> resolveInterface(Class<?> type) {
        Class classToCreate = registers.get(type);
        if (classToCreate == null) {
            classToCreate = type;
        }
        if (type == Byte.TYPE
                || type == Byte.class
                || type == Boolean.TYPE
                || type == Boolean.class
                || type == Short.TYPE
                || type == Short.class
                || type == Integer.TYPE
                || type == Integer.class
                || type == Long.TYPE
                || type == Long.class
                || type == Float.TYPE
                || type == Float.class
                || type == Double.TYPE
                || type == Double.class
                ) {
            throw ErrorContextFactory.instance().message("创建实例时,implementClass参数'{}'是基本类型", classToCreate).runtimeException();
        }
        if (classToCreate.isInterface()) {
            throw ErrorContextFactory.instance().message("创建实例时,implementClass参数'{}'是接口", classToCreate).solution("使用'{}'接口的实现类", classToCreate).runtimeException();
        }
        if (classToCreate.getModifiers() == Modifier.ABSTRACT) {
            throw ErrorContextFactory.instance().message("创建实例时,implementClass参数'{}'是抽象的", classToCreate).solution("使用'{}'接口的非抽象实现类", classToCreate).runtimeException();
        }
        return classToCreate;
    }

    public <T> boolean isCollection(Class<T> type) {
        return Collection.class.isAssignableFrom(type);
    }

}