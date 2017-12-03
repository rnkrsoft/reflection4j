package com.devops4j.reflection4j.factory;

import com.devops4j.reflection4j.MetaObject;
import com.devops4j.reflection4j.ObjectWrapper;
import com.devops4j.reflection4j.ObjectWrapperFactory;
import com.devops4j.logtrace4j.ErrorContextFactory;

public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {

    public boolean hasWrapperFor(Object object) {
        return false;
    }

    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        ErrorContextFactory.instance().message("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.").throwError();
        return null;
    }

}