package com.rnkrsoft.reflection4j.factory;

import com.rnkrsoft.reflection4j.MetaObject;
import com.rnkrsoft.reflection4j.ObjectWrapper;
import com.rnkrsoft.reflection4j.ObjectWrapperFactory;
import com.rnkrsoft.logtrace4j.ErrorContextFactory;

public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {

    public boolean hasWrapperFor(Object object) {
        return false;
    }

    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        throw ErrorContextFactory.instance().message("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.").runtimeException();
    }

}