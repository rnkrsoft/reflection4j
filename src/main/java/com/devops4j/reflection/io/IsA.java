package com.devops4j.reflection.io;

/**
 * A Test that checks to see if each class is assignable to the provided class. Note
 * that this test will match the parent type itself if it is presented for matching.
 */
public class IsA implements Test {
    private Class<?> parent;

    /**
     * Constructs an IsA test using the supplied Class as the parent class/interface.
     */
    public IsA(Class<?> parentType) {
        this.parent = parentType;
    }

    /**
     * Returns true if type is assignable to the parent type supplied in the constructor.
     */
    public boolean matches(Class<?> type) {
        //parent类是type类的父类，是则true,否则false;
        return type != null && parent.isAssignableFrom(type);
    }

    @Override
    public String toString() {
        return "is assignable to " + parent.getSimpleName();
    }
}