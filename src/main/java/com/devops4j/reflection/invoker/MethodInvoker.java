package com.devops4j.reflection.invoker;

import com.devops4j.reflection.Invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker implements Invoker {

    private Class<?> type;
    private Method method;

    public MethodInvoker(Method method) {
        this.method = method;

        if (method.getParameterTypes().length == 1) {
            type = method.getParameterTypes()[0];
        } else {
            type = method.getReturnType();
        }
    }

    public Object invoke(Object target, Object... args) throws Exception {
        try {
            return method.invoke(target, args);
        } catch (InvocationTargetException e) {
            Throwable throwable = e.getTargetException();
            return throwable;
        }
    }

    public Class<?> getType() {
        return type;
    }
}
