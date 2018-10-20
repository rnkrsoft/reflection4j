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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

/**
 * Created by rnkrsoft on 2017/5/28.
 */
public interface Reflector {
    /**
     * 获取反射的目标类
     *
     * @return 类对象
     */
    Class getType();

    /**
     * 获取无参构造函数构造器
     *
     * @return 函数构造器
     */
    Constructor getDefaultConstructor();

    Constructor getConstructor(Class...classes);

    /**
     * 是否存在无参构造函数
     *
     * @return
     */
    boolean hasDefaultConstructor();


    boolean hasConstructor(Class...classes);
    /**
     * 获取方法执行器
     * @param name 方法名
     * @param paramTypes 参数类型
     * @return 执行器
     */
    Invoker getMethodInvoker(String name, Class... paramTypes);
    /**
     * 根据属性名获取执行器
     *
     * @param propertyName 属性名
     * @return 执行器
     */
    Invoker getSetter(String propertyName);

    /**
     * 根据属性名获取执行器
     *
     * @param propertyName 属性名
     * @return 执行器
     */
    Invoker getGetter(String propertyName);

    /**
     * 根据属性名获取类对象
     *
     * @param propertyName 属性名
     * @return 类型
     */
    Class<?> getSetterType(String propertyName);

    /**
     * 根据属性名获取类对象
     *
     * @param propertyName 属性名
     * @return 类型
     */
    Class<?> getGetterType(String propertyName);

    /**
     * 获取可进行Get属性名数组
     *
     * @return 属性名数组
     */
    Collection<String> getGettablePropertyNames();

    /**
     * 获取可进行Set属性名数组
     *
     * @return 属性名数组
     */
    Collection<String> getSettablePropertyNames();

    /**
     * 根据属性名检测是否有Setter
     *
     * @param propertyName 属性名
     * @return 布尔值
     */
    boolean hasSetter(String propertyName);

    /**
     * 根据属性名检测是否有Getter
     *
     * @param propertyName 属性名
     * @return 布尔值
     */
    boolean hasGetter(String propertyName);

    /**
     * 检测是否存在属性
     * @param propertyName
     * @return
     */
    boolean hasProperty(String propertyName);

    /**
     * 根据属性名获取Field对象
     * @param propertyName 属性名
     * @return Field对象
     */
    Field getField(String propertyName);

    /**
     * 获取类得字段
     * @return 字段
     */
    List<Field> getFields();
}
