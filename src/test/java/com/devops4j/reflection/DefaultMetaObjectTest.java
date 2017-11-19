package com.devops4j.reflection;

import com.devops4j.reflection.factory.MetaObjectFactory;
import com.devops4j.reflection.wrapper.Author;
import com.devops4j.reflection.wrapper.RichType;
import org.junit.Test;
import com.devops4j.reflection.wrapper.Section;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by devops4j on 2017/7/25.
 */
public class DefaultMetaObjectTest {

    @Test
    public void shouldGetAndSetField() {
        RichType rich = new RichType();
        MetaObject meta = MetaObjectFactory.forObject(rich);
        meta.setValue("richField", "foo");
        assertEquals("foo", meta.getValue("richField"));
    }

    @Test
    public void shouldGetAndSetNestedField() {
        RichType rich = new RichType();
        MetaObject meta = MetaObjectFactory.forObject(rich);
        meta.setValue("richType.richField", "foo");
        assertEquals("foo", meta.getValue("richType.richField"));
    }

    @Test
    public void shouldGetAndSetProperty() {
        RichType rich = new RichType();
        MetaObject meta = MetaObjectFactory.forObject(rich);
        meta.setValue("richProperty", "foo");
        assertEquals("foo", meta.getValue("richProperty"));
    }

    @Test
    public void shouldGetAndSetNestedProperty() {
        RichType rich = new RichType();
        MetaObject meta = MetaObjectFactory.forObject(rich);
        meta.setValue("richType.richProperty", "foo");
        assertEquals("foo", meta.getValue("richType.richProperty"));
    }

    @Test
    public void shouldGetAndSetMapPair() {
        RichType rich = new RichType();
        MetaObject meta = MetaObjectFactory.forObject(rich);
        meta.setValue("richMap.key", "foo");
        assertEquals("foo", meta.getValue("richMap.key"));
    }

    @Test
    public void shouldGetAndSetMapPairUsingArraySyntax() {
        RichType rich = new RichType();
        MetaObject meta = MetaObjectFactory.forObject(rich);
        meta.setValue("richMap[key]", "foo");
        assertEquals("foo", meta.getValue("richMap[key]"));
    }

    @Test
    public void shouldGetAndSetNestedMapPair() {
        RichType rich = new RichType();
        MetaObject meta = MetaObjectFactory.forObject(rich);
        meta.setValue("richType.richMap.key", "foo");
        assertEquals("foo", meta.getValue("richType.richMap.key"));
    }

    @Test
    public void shouldGetAndSetNestedMapPairUsingArraySyntax() {
        RichType rich = new RichType();
        MetaObject meta = MetaObjectFactory.forObject(rich);
        meta.setValue("richType.richMap[key]", "foo");
        assertEquals("foo", meta.getValue("richType.richMap[key]"));
    }

    @Test
    public void shouldGetAndSetListItem() {
        RichType rich = new RichType();
        MetaObject meta = MetaObjectFactory.forObject(rich);
        meta.setValue("richList[0]", "foo");
        assertEquals("foo", meta.getValue("richList[0]"));
    }

    @Test
    public void shouldSetAndGetSelfListItem() {
        RichType rich = new RichType();
        MetaObject meta = MetaObjectFactory.forObject(rich);
        meta.setValue("richList[0]", "foo");
        assertEquals("foo", meta.getValue("richList[0]"));
    }

    @Test
    public void shouldGetAndSetNestedListItem() {
        RichType rich = new RichType();
        MetaObject meta = MetaObjectFactory.forObject(rich);
        meta.setValue("richType.richList[0]", "foo");
        assertEquals("foo", meta.getValue("richType.richList[0]"));
    }

    @Test
    public void shouldGetReadablePropertyNames() {
        RichType rich = new RichType();
        MetaObject meta = MetaObjectFactory.forObject(rich);
        String[] readables = meta.getGetterNames();
        assertEquals(5, readables.length);
        for (String readable : readables) {
            assertTrue(meta.hasGetter(readable));
            assertTrue(meta.hasGetter("richType." + readable));
        }
        assertTrue(meta.hasGetter("richType"));
    }

    @Test
    public void shouldGetWriteablePropertyNames() {
        RichType rich = new RichType();
        MetaObject meta = MetaObjectFactory.forObject(rich);
        String[] writeables = meta.getSetterNames();
        assertEquals(5, writeables.length);
        for (String writeable : writeables) {
            assertTrue(meta.hasSetter(writeable));
            assertTrue(meta.hasSetter("richType." + writeable));
        }
        assertTrue(meta.hasSetter("richType"));
    }

    @Test
    public void shouldSetPropertyOfNullNestedProperty() {
        MetaObject richWithNull = MetaObjectFactory.forObject(new RichType());
        richWithNull.setValue("richType.richProperty", "foo");
        assertEquals("foo", richWithNull.getValue("richType.richProperty"));
    }

    @Test
    public void shouldSetPropertyOfNullNestedPropertyWithNull() {
        MetaObject richWithNull = MetaObjectFactory.forObject(new RichType());
        richWithNull.setValue("richType.richProperty", null);
        assertEquals(null, richWithNull.getValue("richType.richProperty"));
    }

    @Test
    public void shouldGetPropertyOfNullNestedProperty() {
        MetaObject richWithNull = MetaObjectFactory.forObject(new RichType());
        assertNull(richWithNull.getValue("richType.richProperty"));
    }

    @Test
    public void shouldVerifyHasReadablePropertiesReturnedByGetReadablePropertyNames() {
        MetaObject object = MetaObjectFactory.forObject(new Author());
        for (String readable : object.getGetterNames()) {
            assertTrue(object.hasGetter(readable));
        }
    }

    @Test
    public void shouldVerifyHasWriteablePropertiesReturnedByGetWriteablePropertyNames() {
        MetaObject object = MetaObjectFactory.forObject(new Author());
        for (String writeable : object.getSetterNames()) {
            assertTrue(object.hasSetter(writeable));
        }
    }

    @Test
    public void shouldSetAndGetProperties() {
        MetaObject object = MetaObjectFactory.forObject(new Author());
        object.setValue("email", "test");
        assertEquals("test", object.getValue("email"));

    }

    @Test
    public void shouldVerifyPropertyTypes() {
        MetaObject object = MetaObjectFactory.forObject(new Author());
        assertEquals(6, object.getSetterNames().length);
        assertEquals(int.class, object.getGetterType("id"));
        assertEquals(String.class, object.getGetterType("username"));
        assertEquals(String.class, object.getGetterType("password"));
        assertEquals(String.class, object.getGetterType("email"));
        assertEquals(String.class, object.getGetterType("bio"));
        assertEquals(Section.class, object.getGetterType("favouriteSection"));
    }

    @Test
    public void shouldDemonstrateDeeplyNestedMapProperties() {
        HashMap<String, String> map = new HashMap<String, String>();
        MetaObject metaMap = MetaObjectFactory.forObject(map);

        assertTrue(metaMap.hasSetter("id"));
        assertTrue(metaMap.hasSetter("name.first"));
        assertTrue(metaMap.hasSetter("address.street"));

        assertFalse(metaMap.hasGetter("id"));
        assertFalse(metaMap.hasGetter("name.first"));
        assertFalse(metaMap.hasGetter("address.street"));

        metaMap.setValue("id", "100");
        metaMap.setValue("name.first", "Clinton");
        metaMap.setValue("name.last", "Begin");
        metaMap.setValue("address.street", "1 Some Street");
        metaMap.setValue("address.city", "This City");
        metaMap.setValue("address.province", "A Province");
        metaMap.setValue("address.postal_code", "1A3 4B6");

        assertTrue(metaMap.hasGetter("id"));
        assertTrue(metaMap.hasGetter("name.first"));
        assertTrue(metaMap.hasGetter("address.street"));

        assertEquals(3, metaMap.getGetterNames().length);
        assertEquals(3, metaMap.getSetterNames().length);

        Map<String,String> name = (Map<String,String>) metaMap.getValue("name");
        Map<String,String> address = (Map<String,String>) metaMap.getValue("address");

        assertEquals("Clinton", name.get("first"));
        assertEquals("1 Some Street", address.get("street"));
    }

    @Test
    public void shouldDemonstrateNullValueInMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        MetaObject metaMap = MetaObjectFactory.forObject(map);
        assertFalse(metaMap.hasGetter("phone.home"));

        metaMap.setValue("phone", null);
        assertTrue(metaMap.hasGetter("phone"));
        // hasGetter returns true if the parent exists and is null.
        assertTrue(metaMap.hasGetter("phone.home"));
        assertTrue(metaMap.hasGetter("phone.home.ext"));
        assertNull(metaMap.getValue("phone"));
        assertNull(metaMap.getValue("phone.home"));
        assertNull(metaMap.getValue("phone.home.ext"));

        metaMap.setValue("phone.office", "789");
        assertFalse(metaMap.hasGetter("phone.home"));
        assertFalse(metaMap.hasGetter("phone.home.ext"));
        assertEquals("789", metaMap.getValue("phone.office"));
        assertNotNull(metaMap.getValue("phone"));
        assertNull(metaMap.getValue("phone.home"));
    }
//
//    @Test
//    public void shouldNotUseObjectWrapperFactoryByDefault() {
//        MetaObject meta = MetaObjectFactory.forObject(new Author());
//        assertTrue(!meta.getObjectWrapper().getClass().equals(CustomBeanWrapper.class));
//    }
//
//    @Test
//    public void shouldUseObjectWrapperFactoryWhenSet() {
//        MetaObject meta = MetaObject.forObject(new Author(), MetaObjectFactory.DEFAULT_OBJECT_FACTORY, new CustomBeanWrapperFactory(), new DefaultReflectorFactory());
//        assertTrue(meta.getObjectWrapper().getClass().equals(CustomBeanWrapper.class));
//
//        // Make sure the old default factory is in place and still works
//        meta = MetaObjectFactory.forObject(new Author());
//        assertFalse(meta.getObjectWrapper().getClass().equals(CustomBeanWrapper.class));
//    }

    @Test
    public void shouldMethodHasGetterReturnTrueWhenListElementSet() {
        List<Object> param1 = new ArrayList<Object>();
        param1.add("firstParam");
        param1.add(222);
        param1.add(new Date());

        Map<String, Object> parametersEmulation = new HashMap<String, Object>();
        parametersEmulation.put("param1", param1);
        parametersEmulation.put("filterParams", param1);

        MetaObject meta = MetaObjectFactory.forObject(parametersEmulation);

        assertEquals(param1.get(0), meta.getValue("filterParams[0]"));
        assertEquals(param1.get(1), meta.getValue("filterParams[1]"));
        assertEquals(param1.get(2), meta.getValue("filterParams[2]"));

        assertTrue(meta.hasGetter("filterParams[0]"));
        assertTrue(meta.hasGetter("filterParams[1]"));
        assertTrue(meta.hasGetter("filterParams[2]"));
    }
}