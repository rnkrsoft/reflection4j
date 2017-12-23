package com.devops4j.reflection4j;

import com.devops4j.reflection4j.resource.ClassScanner;
import com.devops4j.reflection4j.scanner.B;
import com.devops4j.reflection4j.scanner.ann.Ann;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by devops4j on 2017/12/2.
 */
public class ClassScannerTest {

    @Test
    public void testScan() throws Exception {
        ClassScanner scanner = new ClassScanner();
        scanner.scan("com.devops4j.reflection4j.scanner");
        Assert.assertEquals(2, scanner.getClasses().size());
    }

    @Test
    public void testScan1() throws Exception {
        ClassScanner scanner = new ClassScanner();
        scanner.scan("junit.extensions");
        Assert.assertEquals(6, scanner.getClasses().size());
        scanner.scan("junit.extensions");
        Assert.assertEquals(6, scanner.getClasses().size());
        scanner.clear();
        scanner.scan("junit.extensions");
        Assert.assertEquals(6, scanner.getClasses().size());
    }

    @Test
    public void testScan2() throws Exception {
        ClassScanner scanner = new ClassScanner(true);
        scanner.scan("com.devops4j.reflection4j.scanner");
        Assert.assertEquals(4, scanner.getClasses().size());
        scanner.clear();
        scanner.scan("com.devops4j.reflection4j.scanner", new ClassScanner.Filter() {
            public boolean accept(Class clazz) {
                Ann ann = (Ann) clazz.getAnnotation(Ann.class);
                return ann != null;
            }
        });
        Assert.assertEquals(1, scanner.getClasses().size());
    }

    @Test
    public void testScan3() throws Exception {
        ClassScanner scanner = new ClassScanner(true);
        scanner.scan("com.devops4j.reflection4j.scanner", new ClassScanner.IncludeMethodNameFilter("fun1"));
        Assert.assertEquals(2, scanner.getClasses().size());
        scanner.clear();
        scanner.scan("com.devops4j.reflection4j.scanner", new ClassScanner.IncludeMethodNameFilter("fun2"));
        Assert.assertEquals(2, scanner.getClasses().size());
        scanner.clear();
        scanner.scan("com.devops4j.reflection4j.scanner", new ClassScanner.IsAFilter(B.class));
        System.out.println(scanner.getClasses());
        Assert.assertEquals(2, scanner.getClasses().size());
        scanner = new ClassScanner(false);
        scanner.scan("com.devops4j.reflection4j.scanner", new ClassScanner.IncludeMethodNameFilter("fun1"));
        Assert.assertEquals(1, scanner.getClasses().size());
        scanner.clear();
        scanner.scan("com.devops4j.reflection4j.scanner", new ClassScanner.IncludeMethodNameFilter("fun2"));
        Assert.assertEquals(2, scanner.getClasses().size());
    }
}