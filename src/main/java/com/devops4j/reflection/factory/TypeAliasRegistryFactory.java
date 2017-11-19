package com.devops4j.reflection.factory;

import com.devops4j.reflection.DefaultTypeAliasRegistry;
import com.devops4j.reflection.TypeAliasRegistry;

/**
 * Created by devops4j on 2017/7/12.
 */
public class TypeAliasRegistryFactory {
    private static final TypeAliasRegistry TYPE_ALIAS_REGISTRY = new DefaultTypeAliasRegistry();
    public static TypeAliasRegistry getInstance(){
        return TYPE_ALIAS_REGISTRY;
    }
}
