package com.devops4j.reflection.invoker;

import com.devops4j.reflection.Invoker;

import java.lang.reflect.Field;


public class GetFieldInvoker implements Invoker {
  private Field field;

  public GetFieldInvoker(Field field) {
    this.field = field;
  }

  public Object invoke(Object target, Object... args) throws Exception {
    return field.get(target);
  }

  public Class<?> getType() {
    return field.getType();
  }
}
