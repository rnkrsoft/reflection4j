package com.rnkrsoft.reflection4j.extension.compiler;

import com.rnkrsoft.reflection4j.extension.ExtensionLoader;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;


/**
 * Created by rnkrsoft.com on 2019/8/23.
 */
public class CompilerTest {

    @Test
    public void testCompile() throws Exception {
        Compiler compiler = new JavassistCompiler();
        Class clazz = compiler.compile("package com.rnkrsoft.demo;\npublic class Demoxxx {\n public String say(){ return \"this is a test\";\n}}", new File("D:/temp"), getClass().getClassLoader());
        String name = clazz.getName();
        System.out.println(name);
        System.out.println(Arrays.toString(clazz.getMethods()));
    }

    @Test
    public void testCompile2() throws Exception {
        Compiler compiler = ExtensionLoader.getExtensionLoader(Compiler.class).getExtension("jdk");
        Class clazz = compiler.compile("package com.rnkrsoft.demo;\npublic class Demoxxx {\n public String say(){ return \"this is a test\";\n}}", new File("D:/temp"), getClass().getClassLoader());
        String name = clazz.getName();
        System.out.println(name);
        System.out.println(Arrays.toString(clazz.getMethods()));
    }
}