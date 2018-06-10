package com.rnkrsoft.reflection4j.factory;

import com.rnkrsoft.reflection4j.ObjectFactory;
import com.rnkrsoft.logtrace4j.ErrorContextFactory;
import com.rnkrsoft.logtrace4j.TraceableRuntimeException;

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