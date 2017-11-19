package com.devops4j.reflection.invoker;

import com.devops4j.reflection.Invoker;
import com.devops4j.track.ErrorContextFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class SetFieldInvoker implements Invoker {
  private Field field;

  public SetFieldInvoker(Field field) {
    this.field = field;
  }

  public Object invoke(Object target, Object... args) throws IllegalAccessException, InvocationTargetException {
    if(args.length != 1){
      ErrorContextFactory.instance().message("参数只能为1个值!").throwError();
      return null;
    }
    field.set(target, args[0]);
    return null;
  }

  public Class<?> getType() {
    return field.getType();
  }
}
