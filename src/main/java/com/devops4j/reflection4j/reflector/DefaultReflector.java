package com.devops4j.reflection4j.reflector;

import com.devops4j.reflection4j.Invoker;
import com.devops4j.reflection4j.Reflector;
import com.devops4j.reflection4j.invoker.GetFieldInvoker;
import com.devops4j.reflection4j.invoker.MethodInvoker;
import com.devops4j.reflection4j.invoker.SetFieldInvoker;
import com.devops4j.reflection4j.property.PropertyNamer;
import com.devops4j.track.ErrorContextFactory;

import java.lang.reflect.*;
import java.util.*;

/**
 * 默认实现的反射器接口
 */
public class DefaultReflector implements Reflector {
    static final Set<String> IGNORE_FIELD = new HashSet();

    static final String[] EMPTY_STRING_ARRAY = new String[0];

    Class<?> type;
    String[] readablePropertyNames = EMPTY_STRING_ARRAY;
    String[] writablePropertyNames = EMPTY_STRING_ARRAY;
    Map<String, Invoker> setMethods = new HashMap();
    Map<String, Invoker> getMethods = new HashMap();
    Map<String, Class<?>> setTypes = new HashMap();
    Map<String, Class<?>> getTypes = new HashMap();
    Map<String, Field> fields = new HashMap();
    Constructor<?> defaultConstructor;

    static {
        IGNORE_FIELD.add("serialPersistentFields");
        IGNORE_FIELD.add("CASE_INSENSITIVE_ORDER");
        IGNORE_FIELD.add("bytes");
        IGNORE_FIELD.add("class");
        IGNORE_FIELD.add("value");
        IGNORE_FIELD.add("hash");
        IGNORE_FIELD.add("empty");
    }

    public DefaultReflector(Class<?> clazz) {
        type = clazz;
        addDefaultConstructor(clazz);
        addGetMethods(clazz);
        addSetMethods(clazz);
        addFields(clazz);
        readablePropertyNames = getMethods.keySet().toArray(new String[getMethods.keySet().size()]);
        writablePropertyNames = setMethods.keySet().toArray(new String[setMethods.keySet().size()]);
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
//        if (!found) {
//            ErrorContextFactory.instance().message("类'{}'不存在无参构造函数", clazz).solution("类'{}'增加无参构造函数'public {}(){}'", clazz, clazz.getSimpleName(), "{}").throwError();
//            return;
//        }
    }

    void addGetMethods(Class<?> cls) {
        Map<String, List<Method>> conflictingGetters = new HashMap<String, List<Method>>();
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
                        ErrorContextFactory.instance().message("类'{}'中存在冲突的Getter方法的重载方法'{}'.不符合JavaBean的规范", firstMethod.getDeclaringClass(), propName).throwError();
                        return;
                    } else if (methodType.isAssignableFrom(getterType)) {
                        // OK getter type is descendant
                    } else if (getterType.isAssignableFrom(methodType)) {
                        getter = method;
                        getterType = methodType;
                    } else {
                        ErrorContextFactory.instance().message("类'{}'中存在冲突的Getter方法的重载方法'{}'.不符合JavaBean的规范", firstMethod.getDeclaringClass(), propName).throwError();
                        return;
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
        }
    }

    void addSetMethods(Class<?> cls) {
        Map<String, List<Method>> conflictingSetters = new HashMap<String, List<Method>>();
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
                    ErrorContextFactory.instance().message("类'{}'中存在冲突的Setter方法的重载方法'{}'.不符合JavaBean的规范", firstMethod.getDeclaringClass(), propName).throwError();
                    return;
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
                        ErrorContextFactory.instance().message("类'{}'中存在冲突的Setter方法的重载方法'{}'.不符合JavaBean的规范", firstMethod.getDeclaringClass(), propName).throwError();
                        return;
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
                    // issue #379 - removed the check for final because JDK 1.5 allows
                    // modification of final fields through reflection4j (JSR-133). (JGB)
                    // pr #16 - final static can only be set by the classloader
                    int modifiers = field.getModifiers();
                    if (!(Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers))) {
                        addSetField(field);
                    }
                }
                if (!getMethods.containsKey(field.getName())) {
                    addGetField(field);
                }
                this.fields.put(field.getName(), field);
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
        }
    }

    void addGetField(Field field) {
        if (isValidPropertyName(field.getName())) {
            getMethods.put(field.getName(), new GetFieldInvoker(field));
            getTypes.put(field.getName(), field.getType());
        }
    }

    boolean isValidPropertyName(String name) {
        return !(name.startsWith("$") || "serialVersionUID".equals(name) || "class".equals(name));
    }

    Method[] getClassMethods(Class<?> cls) {
        Map<String, Method> uniqueMethods = new HashMap<String, Method>();
        Class<?> currentClass = cls;
        while (currentClass != null) {
            addUniqueMethods(uniqueMethods, currentClass.getDeclaredMethods());
            // we also need to look for interface methods -
            // because the class may be abstract
            Class<?>[] interfaces = currentClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                addUniqueMethods(uniqueMethods, anInterface.getMethods());
            }

            currentClass = currentClass.getSuperclass();
        }

        Collection<Method> methods = uniqueMethods.values();

        return methods.toArray(new Method[methods.size()]);
    }

    void addUniqueMethods(Map<String, Method> uniqueMethods, Method[] methods) {
        for (Method currentMethod : methods) {
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
            ErrorContextFactory.instance().message("There is no default constructor for {}", type).throwError();
            return null;
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
            ErrorContextFactory.instance().message("There is no setter for property named '{}' in '{}'", propertyName, type).throwError();
            return null;
        }
        return method;
    }

    public Invoker getGetter(String propertyName) {
        Invoker method = getMethods.get(propertyName);
        if (method == null) {
            ErrorContextFactory.instance().message("There is no getter for property named '{}' in '{}'", propertyName, type).throwError();
            return null;
        }
        return method;
    }

    public Class<?> getSetterType(String propertyName) {
        Class<?> clazz = setTypes.get(propertyName);
        if (clazz == null) {
            ErrorContextFactory.instance().message("There is no setter for property named '{}' in '{}'", propertyName, type).throwError();
            return null;
        }
        return clazz;
    }

    public Class<?> getGetterType(String propertyName) {
        Class<?> clazz = getTypes.get(propertyName);
        if (clazz == null) {
            ErrorContextFactory.instance().message("There is no getter for property named '{}' in '{}'", propertyName, type).throwError();
            return null;
        }
        return clazz;
    }

    public Collection getGettablePropertyNames() {
        return Arrays.asList(readablePropertyNames);
    }

    public Collection getSettablePropertyNames() {
        return Arrays.asList(writablePropertyNames);
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


}
