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