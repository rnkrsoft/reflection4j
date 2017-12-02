package com.devops4j.reflection4j.property;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 属性拷贝
 */
public abstract class PropertyCopier {
    /**
     * 复制属性，子类或者父类的
     *
     * @param source 来源对象
     * @param target 目标对象
     */
    public static void copyBeanProperties(Object source, Object target) {
        if (source == null) {
            return;
        }
        copyBeanProperties(source.getClass(), source, target);
    }

    /**
     * 复制属性，子类或者父类的
     *
     * @param type   来源类对象
     * @param source 来源对象
     * @param target 目标对象
     */
    public static void copyBeanProperties(Class<?> type, Object source, Object target) {
        if (source == null) {
            return;
        }
        if (source.getClass() != type) {
            throw new IllegalArgumentException("source bean is not " + type + " instance!");
        }
        if (target == null) {
            try {
                Constructor constructor = type.getConstructor(new Class[0]);
                target = constructor.newInstance(new Object[0]);
            } catch (NoSuchMethodException e) {
            } catch (InvocationTargetException e) {
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            }
            if (target == null) {
                throw new IllegalArgumentException("target bean can not create new  " + type + " instance!");
            }
        }
        if (!target.getClass().isAssignableFrom(type) && !type.isAssignableFrom(target.getClass())) {
            throw new IllegalArgumentException("source bean " + target.getClass() + "is not " + type + " sub class or parent class!");
        }
        Class<?> parent = type;
        while (parent != null) {
            final Field[] fields = parent.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    field.set(target, field.get(source));
                } catch (Exception e) {
                    // Nothing useful to do, will only fail on final fields, which will be ignored.
                }
            }
            parent = parent.getSuperclass();
        }
    }

}