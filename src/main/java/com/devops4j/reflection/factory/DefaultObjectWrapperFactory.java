package com.devops4j.reflection.factory;

import com.devops4j.reflection.MetaObject;
import com.devops4j.reflection.ObjectWrapper;
import com.devops4j.reflection.ObjectWrapperFactory;
import com.devops4j.track.ErrorContextFactory;

public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {

    public boolean hasWrapperFor(Object object) {
        return false;
    }

    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        ErrorContextFactory.instance().message("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.").throwError();
        return null;
    }

}