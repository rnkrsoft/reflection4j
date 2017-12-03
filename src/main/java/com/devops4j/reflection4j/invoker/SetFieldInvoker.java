package com.devops4j.reflection4j.invoker;

import com.devops4j.reflection4j.Invoker;
import com.devops4j.track.ErrorContextFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SetFieldInvoker implements Invoker {
    private Field field;
    private Map<String, Class<?>> paramNamesClass = new HashMap<String, Class<?>>();

    public SetFieldInvoker(Field field) {
        this.field = field;
        paramNamesClass.put(field.getName(), field.getType());
    }

    public Object invoke(Object target, Object... args) throws Exception {
        if (args.length != 1) {
            ErrorContextFactory.instance().message("参数只能为1个值!").throwError();
            return null;
        }
        field.set(target, args[0]);
        return null;
    }

    public <T> T invoke(Object target, Map<String, Object> args) throws Exception {
        ErrorContextFactory.instance().message("该执行器不支持该方式调用!").throwError();
        return null;
    }

    public Class<?> getReturnClass() {
        return null;
    }

    public Map<String, Class<?>> getParamNameClass() {

        return paramNamesClass;
    }

    public Class<?>[] getParamIndexClass() {
        return new Class[]{field.getType()};
    }
}
