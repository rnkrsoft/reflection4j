package com.devops4j.reflection4j.invoker;

import com.devops4j.reflection4j.Invoker;
import com.devops4j.track.ErrorContextFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


public class GetFieldInvoker implements Invoker {
    private Field field;

    public GetFieldInvoker(Field field) {
        this.field = field;
    }

    public Object invoke(Object target, Object... args) throws Exception {
        return field.get(target);
    }

    public <T> T invoke(Object target, Map<String, Object> args) throws Exception {
        ErrorContextFactory.instance().message("该执行器不支持该方式调用!").throwError();
        return null;
    }

    public Class<?> getReturnClass() {
        return field.getType();
    }

    public Map<String, Class<?>> getParamNameClass() {
        return new HashMap<String, Class<?>>();
    }

    public Class<?>[] getParamIndexClass() {
        return new Class<?>[0];
    }

    public Class<?> getParamClass() {
        return field.getType();
    }
}
