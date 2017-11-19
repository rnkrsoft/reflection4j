package com.devops4j.reflection.io;

import java.lang.annotation.Annotation;

/**
 * A Test that checks to see if each class is annotated with a specific annotation. If it
 * is, then the test returns true, otherwise false.
 */
public class AnnotatedWith implements Test {
    private Class<? extends Annotation> annotation;

    /**
     * Constructs an AnnotatedWith test for the specified annotation type.
     */
    public AnnotatedWith(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    /**
     * Returns true if the type is annotated with the class provided to the constructor.
     */
    public boolean matches(Class<?> type) {
        return type != null && type.isAnnotationPresent(annotation);
    }

    @Override
    public String toString() {
        return "annotated with @" + annotation.getSimpleName();
    }
}