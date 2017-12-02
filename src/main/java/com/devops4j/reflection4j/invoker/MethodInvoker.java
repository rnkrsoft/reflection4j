package com.devops4j.reflection4j.invoker;

import com.devops4j.reflection4j.Invoker;
import com.devops4j.reflection4j.annotation.Param;
import com.devops4j.track.ErrorContextFactory;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MethodInvoker implements Invoker {
    @Getter
    private Class<?> returnClass;
    private Class<?>[] paramsClass;
    private String[] paramNames;
    private Map<String, Class<?>> paramNamesClass = new HashMap<String, Class<?>>();
    private Method method;

    public MethodInvoker(Method method) {
        this.method = method;
        paramsClass = method.getParameterTypes();
        returnClass = method.getReturnType();
        Annotation[][] anns = method.getParameterAnnotations();
        for (int i = 0; i < anns.length; i++) {
            Annotation[] anns0 = anns[i];
            for (int j = 0; j < anns0.length; j++) {
                Annotation ann = anns0[j];
                if (ann instanceof Param) {
                    Param param = (Param) ann;
                    String name = param.value();
                    Class paramClass = paramsClass[i];
                    paramNamesClass.put(name, paramClass);
                }
            }
        }
    }

    public <T> T invoke(Object target, Object... args) throws Throwable {
        try {
            if (args.length != paramsClass.length) {
                ErrorContextFactory.instance().message("参数实际个数{}与定义个数{}不一致", args.length, paramsClass.length).throwError();
                return null;
            }
            Object[] args1 = new Object[args.length];
            for (int i = 0; i < args.length; i++) {
                Object val = convert(paramsClass[i], args[i]);
                args1[i] = val;
            }
            return (T) method.invoke(target, args1);
        } catch (InvocationTargetException e) {
            Throwable throwable = e.getTargetException();
            throw  throwable;
        }
    }

    public <T> T invoke(Object target, Map<String, Object> args) throws Throwable {
        Object[] args0 = new Object[paramsClass.length];
        for (int i = 0; i < paramNames.length; i++) {
            String argName = paramNames[i];
            Object val = args.get(argName);
            Class paramClass = paramNamesClass.get(argName);
            if(paramClass == null){

            }
            val = convert(paramClass, val);
            args0[i] = val;
        }
        try {
            return (T) method.invoke(target, args0);
        } catch (InvocationTargetException e) {
            Throwable throwable = e.getTargetException();
            throw  throwable;
        }
    }

    /**
     * 转换值
     *
     * @param val
     * @return
     */
    Object convert(Class targetClass, Object val) {
        if (val == null) {
            return null;
        }
        if (targetClass == val.getClass()) {
            return val;
        }
        if (targetClass == Integer.TYPE || targetClass == Integer.class) {
            return Integer.valueOf(val.toString());
        } else if (targetClass == Long.TYPE || targetClass == Long.class) {
            return Long.valueOf(val.toString());
        } else if (targetClass == Boolean.TYPE || targetClass == Boolean.class) {
            return Boolean.valueOf(val.toString());
        } else {
            return val;
        }
    }

    /**
     * 转换值
     *
     * @param val
     * @return
     */
    String convert(Object val) {
        if (val == null) {
            return null;
        }
        if (val.getClass() == Integer.TYPE || val.getClass() == Integer.class) {
            return val.toString();
        } else if (val.getClass() == Long.TYPE || val.getClass() == Long.class) {
            return val.toString();
        } else if (val.getClass() == Boolean.TYPE || val.getClass() == Boolean.class) {
            return val.toString();
        } else {
            return val.toString();
        }
    }



    public Map<String, Class<?>> getParamNameClass() {

        return null;
    }

    public Class<?>[] getParamIndexClass() {
        return new Class<?>[0];
    }
}
