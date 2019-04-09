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
 * 如果您经营一家企业，则禁止在任何设备上阅读Rnkrsoft开源源代码,禁止分析、禁止研究Rnkrsoft开源源代码。
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
 * 本协议所对应的软件为 Rnkrsoft 所拥有的自主知识产权，如果基于本软件进行二次开发，在不改变本软件的任何组成部分的情况下的而二次开发源代码所属版权为贵公司所有。
 */
package com.rnkrsoft.reflection4j.reflector;

import com.rnkrsoft.logtrace4j.ErrorContextFactory;
import com.rnkrsoft.reflection4j.Invoker;
import com.rnkrsoft.reflection4j.Reflector;
import com.rnkrsoft.reflection4j.invoker.GetFieldInvoker;
import com.rnkrsoft.reflection4j.invoker.MethodInvoker;
import com.rnkrsoft.reflection4j.invoker.SetFieldInvoker;
import com.rnkrsoft.reflection4j.property.PropertyNamer;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * 默认实现的反射器接口
 */
public class DefaultReflector implements Reflector {
    static final Set<String> IGNORE_FIELD = new HashSet();
    static final Set<Class> NOT_SUPPORTED = new HashSet();

    Class<?> type;
    List<String> readablePropertyNames = new ArrayList<String>();
    List<String> writablePropertyNames = new ArrayList<String>();
    Map<String, Invoker> setMethods = new LinkedHashMap<String, Invoker>();
    Map<String, Invoker> getMethods = new LinkedHashMap<String, Invoker>();
    Map<String, Class<?>> setTypes = new LinkedHashMap<String, Class<?>>();
    Map<String, Class<?>> getTypes = new LinkedHashMap<String, Class<?>>();
    Map<String, Field> fields = new LinkedHashMap<String, Field>();
    List<Field> orderFields = new ArrayList();
    Constructor<?> defaultConstructor;

    static {
        IGNORE_FIELD.add("CASE_INSENSITIVE_ORDER");
        IGNORE_FIELD.add("class");
        IGNORE_FIELD.add("BYTES");
        IGNORE_FIELD.add("SIZE");
        IGNORE_FIELD.add("MAX_VALUE");
        IGNORE_FIELD.add("MIN_VALUE");
        IGNORE_FIELD.add("TYPE");

        NOT_SUPPORTED.add(Class.class);
        NOT_SUPPORTED.add(Object.class);
        NOT_SUPPORTED.add(String.class);
        NOT_SUPPORTED.add(Byte.class);
        NOT_SUPPORTED.add(Byte.TYPE);
        NOT_SUPPORTED.add(Boolean.class);
        NOT_SUPPORTED.add(Boolean.TYPE);
        NOT_SUPPORTED.add(Short.class);
        NOT_SUPPORTED.add(Short.TYPE);
        NOT_SUPPORTED.add(Integer.class);
        NOT_SUPPORTED.add(Integer.TYPE);
        NOT_SUPPORTED.add(Long.class);
        NOT_SUPPORTED.add(Long.TYPE);
        NOT_SUPPORTED.add(Float.class);
        NOT_SUPPORTED.add(Float.TYPE);
        NOT_SUPPORTED.add(Double.class);
        NOT_SUPPORTED.add(Double.TYPE);
        NOT_SUPPORTED.add(BigDecimal.class);
        NOT_SUPPORTED.add(Date.class);
        NOT_SUPPORTED.add(Time.class);
        NOT_SUPPORTED.add(java.sql.Date.class);
        NOT_SUPPORTED.add(java.sql.Date.class);
    }

    public DefaultReflector(Class<?> clazz) {
        type = clazz;
        if (NOT_SUPPORTED.contains(clazz)){

        }else {
            addDefaultConstructor(clazz);
            addFields(clazz);
            addGetMethods(clazz);
            addSetMethods(clazz);
        }
    }

    void addDefaultConstructor(Class<?> clazz) {
        Constructor<?>[] consts = clazz.getDeclaredConstructors();
        boolean found = false;
        for (Constructor<?> constructor : consts) {
            if (constructor.getParameterTypes().length == 0) {
                if (canAccessPrivateMethods()) {
                    try {
                        constructor.setAccessible(true);
                    } catch (Exception e) {
                        // Ignored. This is only a final precaution, nothing we can do.
                    }
                }

                if (constructor.isAccessible()) {
                    this.defaultConstructor = constructor;
                }
                found = true;
            }
        }
    }

    /**
     * 获取Getter方法数组
     * @param cls
     */
    void addGetMethods(Class<?> cls) {
        Map<String, List<Method>> conflictingGetters = new LinkedHashMap<String, List<Method>>();
        Method[] methods = getClassMethods(cls);
        for (Method method : methods) {
            String name = method.getName();
            if (PropertyNamer.isGetter(name)) {
                name = PropertyNamer.methodToProperty(name);
                if (IGNORE_FIELD.contains(name)) {
                    continue;
                }
                //GETTER为无参
                if (method.getParameterTypes().length == 0) {
                    addMethodConflict(conflictingGetters, name, method);
                }
            }
        }
        resolveGetterConflicts(conflictingGetters);
    }

    void resolveGetterConflicts(Map<String, List<Method>> conflictingGetters) {
        for (String propName : conflictingGetters.keySet()) {
            List<Method> getters = conflictingGetters.get(propName);
            Iterator<Method> iterator = getters.iterator();
            Method firstMethod = iterator.next();
            if (getters.size() == 1) {
                addGetMethod(propName, firstMethod);
            } else {
                Method getter = firstMethod;
                Class<?> getterType = firstMethod.getReturnType();
                while (iterator.hasNext()) {
                    Method method = iterator.next();
                    Class<?> methodType = method.getReturnType();
                    if (methodType.equals(getterType)) {
                        throw ErrorContextFactory.instance().message("类'{}'中存在冲突的Getter方法的重载方法'{}'.不符合JavaBean的规范", firstMethod.getDeclaringClass(), propName).runtimeException();
                    } else if (methodType.isAssignableFrom(getterType)) {
                        // OK getter type is descendant
                    } else if (getterType.isAssignableFrom(methodType)) {
                        getter = method;
                        getterType = methodType;
                    } else {
                        throw ErrorContextFactory.instance().message("类'{}'中存在冲突的Getter方法的重载方法'{}'.不符合JavaBean的规范", firstMethod.getDeclaringClass(), propName).runtimeException();
                    }
                }
                addGetMethod(propName, getter);
            }
        }
    }

    void addGetMethod(String name, Method method) {
        if (isValidPropertyName(name)) {
            getMethods.put(name, new MethodInvoker(method));
            getTypes.put(name, method.getReturnType());
            if (!readablePropertyNames.contains(name)) {
                readablePropertyNames.add(name);
            }
        }
    }

    void addSetMethods(Class<?> cls) {
        Map<String, List<Method>> conflictingSetters = new LinkedHashMap<String, List<Method>>();
        Method[] methods = getClassMethods(cls);
        for (Method method : methods) {
            String name = method.getName();
            if (PropertyNamer.isSetter(name)) {
                name = PropertyNamer.methodToProperty(name);
                if (IGNORE_FIELD.contains(name)) {
                    continue;
                }
                //SETTER为1个参数
                if (method.getParameterTypes().length == 1) {
                    addMethodConflict(conflictingSetters, name, method);
                }
            }
        }
        resolveSetterConflicts(conflictingSetters);
    }

    /**
     * 增加相互干扰的方法
     *
     * @param conflictingMethods
     * @param name
     * @param method
     */
    void addMethodConflict(Map<String, List<Method>> conflictingMethods, String name, Method method) {
        List<Method> list = conflictingMethods.get(name);
        if (list == null) {
            list = new ArrayList<Method>();
            conflictingMethods.put(name, list);
        }
        list.add(method);
    }

    void resolveSetterConflicts(Map<String, List<Method>> conflictingSetters) {
        for (String propName : conflictingSetters.keySet()) {
            List<Method> setters = conflictingSetters.get(propName);
            Method firstMethod = setters.get(0);
            if (setters.size() == 1) {
                addSetMethod(propName, firstMethod);
            } else {
                Class<?> expectedType = getTypes.get(propName);
                if (expectedType == null) {
                    throw ErrorContextFactory.instance().message("类'{}'中存在冲突的Setter方法的重载方法'{}'.不符合JavaBean的规范", firstMethod.getDeclaringClass(), propName).runtimeException();
                } else {
                    Iterator<Method> methods = setters.iterator();
                    Method setter = null;
                    while (methods.hasNext()) {
                        Method method = methods.next();
                        if (method.getParameterTypes().length == 1
                                && expectedType.equals(method.getParameterTypes()[0])) {
                            setter = method;
                            break;
                        }
                    }
                    if (setter == null) {
                        throw ErrorContextFactory.instance().message("类'{}'中存在冲突的Setter方法的重载方法'{}'.不符合JavaBean的规范", firstMethod.getDeclaringClass(), propName).runtimeException();
                    }
                    addSetMethod(propName, setter);
                }
            }
        }
    }

    void addSetMethod(String name, Method method) {
        if (isValidPropertyName(name)) {
            setMethods.put(name, new MethodInvoker(method));
            setTypes.put(name, method.getParameterTypes()[0]);
            if (!writablePropertyNames.contains(name)) {
                writablePropertyNames.add(name);
            }
        }
    }

    void addFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (canAccessPrivateMethods()) {
                try {
                    field.setAccessible(true);
                } catch (Exception e) {
                    // Ignored. This is only a final precaution, nothing we can do.
                }
            }
            if (IGNORE_FIELD.contains(field.getName())) {
                continue;
            }
            if (field.isAccessible()) {
                if (!setMethods.containsKey(field.getName())) {
                    int modifiers = field.getModifiers();
                    if (!(Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers))) {
                        addSetField(field);
                    }
                }
                if (!getMethods.containsKey(field.getName())) {
                    addGetField(field);
                }
                this.fields.put(field.getName(), field);
                this.orderFields.add(field);
            }
        }
        //将父类的属性加入
        if (clazz.getSuperclass() != null) {
            addFields(clazz.getSuperclass());
        }
    }

    void addSetField(Field field) {
        if (isValidPropertyName(field.getName())) {
            setMethods.put(field.getName(), new SetFieldInvoker(field));
            setTypes.put(field.getName(), field.getType());
            if (!writablePropertyNames.contains(field.getName())) {
                writablePropertyNames.add(field.getName());
            }
        }
    }

    void addGetField(Field field) {
        if (isValidPropertyName(field.getName())) {
            getMethods.put(field.getName(), new GetFieldInvoker(field));
            getTypes.put(field.getName(), field.getType());
            if (!readablePropertyNames.contains(field.getName())) {
                readablePropertyNames.add(field.getName());
            }
        }
    }

    boolean isValidPropertyName(String name) {
        return !(name.startsWith("$") || "serialVersionUID".equals(name) || "class".equals(name));
    }

    /**
     * 获取当前类及其父类所有的方法
     * @param cls
     * @return
     */
    Method[] getClassMethods(Class<?> cls) {
        Map<String, Method> uniqueMethods = new LinkedHashMap<String, Method>();
        Class<?> currentClass = cls;
        while (currentClass != null) {
            addUniqueMethods(uniqueMethods, currentClass.getDeclaredMethods());
            //遍历所有接口
            Class<?>[] interfaces = currentClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                addUniqueMethods(uniqueMethods, anInterface.getMethods());
            }
            currentClass = currentClass.getSuperclass();
        }

        Collection<Method> methods = uniqueMethods.values();

        return methods.toArray(new Method[methods.size()]);
    }

    /**
     * 处理完成后的uniqueMethods中只存放着唯一的方法对象
     * @param uniqueMethods
     * @param methods
     */
    void addUniqueMethods(Map<String, Method> uniqueMethods, Method[] methods) {
        for (Method currentMethod : methods) {
            //如果遍历的方法不为桥接方法，则根据方法签字（返回类型#方法名:参数类型1 参数类型n）为唯一键，进行方法的处理
            //如果子类已经覆盖定义的方法，则使用子类的方法
            if (!currentMethod.isBridge()) {
                String signature = getSignature(currentMethod);
                // check to see if the method is already known
                // if it is known, then an extended class must have
                // overridden a method
                if (!uniqueMethods.containsKey(signature)) {
                    if (canAccessPrivateMethods()) {
                        try {
                            currentMethod.setAccessible(true);
                        } catch (Exception e) {
                            // Ignored. This is only a final precaution, nothing we can do.
                        }
                    }

                    uniqueMethods.put(signature, currentMethod);
                }
            }
        }
    }

    /**
     * 获取方法签字
     * @param method
     * @return
     */
    String getSignature(Method method) {
        StringBuilder sb = new StringBuilder();
        Class<?> returnType = method.getReturnType();
        if (returnType != null) {
            sb.append(returnType.getName()).append('#');
        }
        sb.append(method.getName());
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            if (i == 0) {
                sb.append(':');
            } else {
                sb.append(',');
            }
            sb.append(parameters[i].getName());
        }
        return sb.toString();
    }

    private static boolean canAccessPrivateMethods() {
        try {
            SecurityManager securityManager = System.getSecurityManager();
            if (null != securityManager) {
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
        } catch (SecurityException e) {
            return false;
        }
        return true;
    }

    public Class<?> getType() {
        return type;
    }

    public Constructor<?> getDefaultConstructor() {
        if (defaultConstructor != null) {
            return defaultConstructor;
        } else {
            throw ErrorContextFactory.instance().message("There is no default constructor for {}", type).runtimeException();
        }
    }

    public boolean hasDefaultConstructor() {
        return defaultConstructor != null;
    }

    public Invoker getMethodInvoker(String name, Class... classes) {
        return null;
    }

    public Invoker getSetter(String propertyName) {
        Invoker method = setMethods.get(propertyName);
        if (method == null) {
            throw ErrorContextFactory.instance().message("There is no setter for property named '{}' in '{}'", propertyName, type).runtimeException();
        }
        return method;
    }

    public Invoker getGetter(String propertyName) {
        Invoker method = getMethods.get(propertyName);
        if (method == null) {
            throw ErrorContextFactory.instance().message("There is no getter for property named '{}' in '{}'", propertyName, type).runtimeException();
        }
        return method;
    }

    public Class<?> getSetterType(String propertyName) {
        Class<?> clazz = setTypes.get(propertyName);
        if (clazz == null) {
            throw ErrorContextFactory.instance().message("There is no setter for property named '{}' in '{}'", propertyName, type).runtimeException();
        }
        return clazz;
    }

    public Class<?> getGetterType(String propertyName) {
        Class<?> clazz = getTypes.get(propertyName);
        if (clazz == null) {
            throw ErrorContextFactory.instance().message("There is no getter for property named '{}' in '{}'", propertyName, type).runtimeException();
        }
        return clazz;
    }

    public Collection getGettablePropertyNames() {
        return Collections.unmodifiableCollection(readablePropertyNames);
    }

    public Collection getSettablePropertyNames() {
        return Collections.unmodifiableCollection(writablePropertyNames);
    }

    public boolean hasSetter(String propertyName) {
        return setMethods.keySet().contains(propertyName);
    }

    public boolean hasGetter(String propertyName) {
        return getMethods.keySet().contains(propertyName);
    }

    public boolean hasProperty(String propertyName) {
        return fields.containsKey(propertyName);
    }

    public Field getField(String propertyName) {
        Field field = fields.get(propertyName);
        if(field == null){
            throw ErrorContextFactory.instance().message("There is no field for property named '{}' in '{}'", propertyName, type).runtimeException();
        }
        return field;
    }

    public List<Field> getFields() {
        return Collections.unmodifiableList(new ArrayList(orderFields));
    }

}
