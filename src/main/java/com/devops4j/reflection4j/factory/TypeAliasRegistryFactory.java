package com.devops4j.reflection4j.factory;

import com.devops4j.reflection4j.registry.DefaultTypeAliasRegistry;
import com.devops4j.reflection4j.TypeAliasRegistry;

/**
 * Created by devops4j on 2017/7/12.
 */
public class TypeAliasRegistryFactory {
    private static final TypeAliasRegistry TYPE_ALIAS_REGISTRY = new DefaultTypeAliasRegistry();

    public static TypeAliasRegistry getInstance() {
        return TYPE_ALIAS_REGISTRY;
    }
}
