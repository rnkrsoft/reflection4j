package com.rnkrsoft.reflection4j;

import com.rnkrsoft.reflection4j.reflector.DefaultReflector;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

/**
 * Created by rnkrsoft on 2017/7/27.
 */
public class DefaultReflectorTest {

    interface Entity<T> {
        T getId();

        void setId(T id);
    }

    static abstract class AbstractEntity implements Entity<Long> {

        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        final String name;

        public AbstractEntity() {
            this.name = "";
        }

        public AbstractEntity(String name, Long id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }
    }

    static class Section extends AbstractEntity implements Entity<Long> {
        public Section() {
        }

        private Section(String name, Long id) {
            super(name, id);
        }

        int age;

        public int getAge() {
            return age;
        }
    }

    @Test
    public void testGetSetterType() throws Exception {
        Reflector reflector = new DefaultReflector(Section.class);
        Assert.assertEquals(Long.class, reflector.getSetterType("id"));
        System.out.println(reflector.getSettablePropertyNames());
        Assert.assertEquals(3, reflector.getSettablePropertyNames().size());
        boolean b= reflector.hasConstructor(String.class, Long.class);
        Assert.assertEquals(true, b);
    }

    @Test
    public void testGetGetterType() throws Exception {
        Reflector reflector = new DefaultReflector(Section.class);
        Assert.assertEquals(Long.class, reflector.getGetterType("id"));
        Assert.assertEquals(String.class, reflector.getGetterType("name"));
        Assert.assertEquals(3, reflector.getGettablePropertyNames().size());
        boolean b= reflector.hasConstructor(String.class, Long.class);
        Assert.assertEquals(true, b);
    }

    @Test
    public void shouldNotGetClass() throws Exception {
        Reflector reflector = new DefaultReflector(Section.class);
        Assert.assertFalse(reflector.hasGetter("class"));
    }

    @Test
    public void testHasSetter() throws Exception {
        Reflector reflector = new DefaultReflector(Section.class);
        Assert.assertTrue(reflector.hasSetter("name"));
    }

    @Test
    public void testHasGetter() throws Exception {
        Reflector reflector = new DefaultReflector(Section.class);
        Assert.assertTrue(reflector.hasGetter("name"));
    }

    @Test
    public void test1() throws Exception {
        Reflector reflector = new DefaultReflector(Section.class);
        Assert.assertEquals(3, reflector.getFields().size());
    }

}