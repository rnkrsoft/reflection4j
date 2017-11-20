package com.devops4j.reflection.io;

/**
 * A simple interface that specifies how to test classes to determine if they
 * are to be included in the results produced by the ResolverUtil.
 */
public interface Test {
    /**
     * Will be called repeatedly with candidate classes. Must return True if a class
     * is to be included in the results, false otherwise.
     */
    boolean matches(Class<?> type);
}