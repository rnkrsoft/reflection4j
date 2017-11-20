package com.devops4j.reflection.io;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class Resolver<T> {

    /**
     * The set of matches being accumulated.
     */
    private Set<Class<? extends T>> matches = new HashSet<Class<? extends T>>();

    /**
     * The ClassLoader to use when looking for classes. If null then the ClassLoader returned
     * by Thread.currentThread().getContextClassLoader() will be used.
     */
    private ClassLoader classloader;

    /**
     * Provides access to the classes discovered so far. If no calls have been made to
     * any of the {@code find()} methods, this set will be empty.
     *
     * @return the set of classes that have been discovered.
     */
    public Set<Class<? extends T>> getClasses() {
        return matches;
    }

    /**
     * Returns the classloader that will be used for scanning for classes. If no explicit
     * ClassLoader has been set by the calling, the context class loader will be used.
     *
     * @return the ClassLoader that will be used to scan for classes
     */
    public ClassLoader getClassLoader() {
        return classloader == null ? Thread.currentThread().getContextClassLoader() : classloader;
    }

    /**
     * Sets an explicit ClassLoader that should be used when scanning for classes. If none
     * is set then the context classloader will be used.
     *
     * @param classloader a ClassLoader to use when scanning for classes
     */
    public void setClassLoader(ClassLoader classloader) {
        this.classloader = classloader;
    }

    /**
     * Attempts to discover classes that are assignable to the type provided. In the case
     * that an interface is provided this method will collect implementations. In the case
     * of a non-interface class, subclasses will be collected.  Accumulated classes can be
     * accessed by calling {@link #getClasses()}.
     *
     * @param parent       the class of interface to find subclasses or implementations of
     * @param packageNames one or more package names to scan (including subpackages) for classes
     */
    public Resolver<T> findImplementations(Class<?> parent, String... packageNames) {
        if (packageNames == null) {
            return this;
        }

        Test test = new IsA(parent);
        for (String pkg : packageNames) {
            find(test, pkg);
        }

        return this;
    }

    /**
     * Attempts to discover classes that are annotated with the annotation. Accumulated
     * classes can be accessed by calling {@link #getClasses()}.
     *
     * @param annotation   the annotation that should be present on matching classes
     * @param packageNames one or more package names to scan (including subpackages) for classes
     */
    public Resolver<T> findAnnotated(Class<? extends Annotation> annotation, String... packageNames) {
        if (packageNames == null) {
            return this;
        }

        Test test = new AnnotatedWith(annotation);
        for (String pkg : packageNames) {
            find(test, pkg);
        }

        return this;
    }

    /**
     * Scans for classes starting at the package provided and descending into subpackages.
     * Each class is offered up to the Test as it is discovered, and if the Test returns
     * true the class is retained.  Accumulated classes can be fetched by calling
     * {@link #getClasses()}.
     *
     * @param test        an instance of {@link Test} that will be used to filter classes
     * @param packageName the name of the package from which to start scanning for
     *                    classes, e.g. {@code net.sourceforge.stripes}
     */
    public Resolver<T> find(Test test, String packageName) {
        String path = getPackagePath(packageName);

        return this;
    }

    /**
     * Converts a Java package name to a path that can be looked up with a call to
     * {@link ClassLoader#getResources(String)}.
     *
     * @param packageName The Java package name to convert to a path
     */
    protected String getPackagePath(String packageName) {
        return packageName == null ? null : packageName.replace('.', '/');
    }

    /**
     * Add the class designated by the fully qualified class name provided to the set of
     * resolved classes if and only if it is approved by the Test supplied.
     *
     * @param test the test used to determine if the class matches
     * @param fqn  the fully qualified name of a class
     */
    protected void addIfMatching(Test test, String fqn) {
        try {
            String externalName = fqn.substring(0, fqn.indexOf('.')).replace('/', '.');
            ClassLoader loader = getClassLoader();
            if (log.isDebugEnabled()) {
                log.debug("Checking to see if class " + externalName + " matches criteria [" + test + "]");
            }

            Class<?> type = loader.loadClass(externalName);
            if (test.matches(type)) {
                matches.add((Class<T>) type);
            }
        } catch (Throwable t) {
            log.warn("Could not examine class '" + fqn + "'" + " due to a " +
                    t.getClass().getName() + " with message: " + t.getMessage());
        }
    }
}