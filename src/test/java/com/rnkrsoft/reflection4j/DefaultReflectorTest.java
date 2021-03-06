package com.rnkrsoft.reflection4j;

import com.rnkrsoft.reflection4j.reflector.DefaultReflector;
import org.junit.Assert;
import org.junit.Test;

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
        String value;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
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

        public Section(String name, Long id) {
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
        System.out.println(reflector.getGettablePropertyNames());
        System.out.println(reflector.getFields());
        Assert.assertEquals(4, reflector.getSettablePropertyNames().size());

    }

    @Test
    public void testGetGetterType() throws Exception {
        Reflector reflector = new DefaultReflector(Section.class);
        Assert.assertEquals(Long.class, reflector.getGetterType("id"));
        Assert.assertEquals(String.class, reflector.getGetterType("name"));
        Assert.assertEquals(String.class, reflector.getGetterType("value"));
        Assert.assertEquals(4, reflector.getGettablePropertyNames().size());
        System.out.println(reflector.getGettablePropertyNames());
    }

    @Test
    public void shouldNotGetClass() throws Exception {
        Reflector reflector = new DefaultReflector(Section.class);
        Assert.assertFalse(reflector.hasGetter("class"));
        Assert.assertTrue(reflector.hasGetter("value"));
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
        Assert.assertEquals(4, reflector.getFields().size());
        System.out.println(reflector.getFields());
    }

}