package com.rnkrsoft.reflection4j;

import com.rnkrsoft.reflection4j.factory.*;
import com.rnkrsoft.reflection4j.registry.DefaultTypeAliasRegistry;
import com.rnkrsoft.reflection4j.wrapper.NullObject;

/**
 * 全局系统元信息
 */
public abstract class GlobalSystemMetadata {
    public static final ObjectFactory OBJECT_FACTORY = new DefaultObjectFactory();
    public static final ObjectWrapperFactory OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    public static final ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();
    public static final TypeAliasRegistry TYPE_ALIAS_REGISTRY = new DefaultTypeAliasRegistry();
    public static final MetaClassFactory META_CLASS_FACTORY = new MetaClassFactory(REFLECTOR_FACTORY);
    public static final MetaObjectFactory META_OBJECT_FACTORY = new MetaObjectFactory(OBJECT_FACTORY, OBJECT_WRAPPER_FACTORY, REFLECTOR_FACTORY, META_CLASS_FACTORY);
    public static final MetaClass NULL_META_CLASS = META_CLASS_FACTORY.forClass(NullObject.class);

    private GlobalSystemMetadata() {
        // Prevent Instantiation of Static Class
    }

    /**
     * 获取对象元信息
     * @param object 对象
     * @return 对象元信息
     */
    public static MetaObject forObject(Class type, Object object) {
        //从名称为""开始构建，
        return META_OBJECT_FACTORY.forObject("",type, object);
    }

    /**
     * 获取类元信息
     * @param type 类型
     * @return 类元信息
     */
    public static MetaClass forClass(Class<?> type) {
        return META_CLASS_FACTORY.forClass(type);
    }
    /**
     * 创建反射器
     * @param type 类型
     * @return 反射器
     */
    public static Reflector reflector(Class<?> type){
        return REFLECTOR_FACTORY.reflector(type);
    }
    
    /**
     * 创建实例
     * @param type 类型
     * @param <T>  实例
     * @return 实例
     */
    public static <T> T create(Class type){
        return (T) OBJECT_FACTORY.create(type);
    }

    /**
     * 根据有参构造函数创建实例
     * @param type 类型
     * @param constructorArgTypes 参数类型
     * @param constructorArgs 参数值
     * @param <T>  实例
     * @return 实例
     */
    public static <T> T create(Class type, Class[] constructorArgTypes, Object[] constructorArgs){
        return (T) OBJECT_FACTORY.create(type, constructorArgTypes, constructorArgs);
    }

    /**
     * 根据别名创建实例
     * @param alias 别名
     * @param <T> 实例
     * @return 实例
     */
    public static <T> T create(String alias){
        Class type = TYPE_ALIAS_REGISTRY.resolveAlias(alias);
        return (T) OBJECT_FACTORY.create(type);
    }

    /**
     * 根据别名创建实例
     * @param alias 别名
     * @param <T> 实例
     * @return 实例
     */
    public static <T> T create(String alias, Class[] constructorArgTypes, Object[] constructorArgs){
        Class type = TYPE_ALIAS_REGISTRY.resolveAlias(alias);
        return (T) OBJECT_FACTORY.create(type, constructorArgTypes, constructorArgs);
    }
}