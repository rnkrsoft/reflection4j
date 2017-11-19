package com.devops4j.reflection.property;

import java.util.Iterator;

/**
 * 属性标记器
 */
public class PropertyTokenizer implements Iterable<PropertyTokenizer>, Iterator<PropertyTokenizer> {
    private String name;
    private String indexedName;
    private String index;
    private String children;

    /**
     *
     * 接受格式
     * beanName.fieldName
     * beanName[x].fieldName
     * beanName.fieldName1.fieldName2
     * beanName[x].fieldName1[y].fieldName2
     * beanName[x].fieldName1.fieldName2
     * @param fullname 属性名
     */
    public PropertyTokenizer(String fullname) {
        // 对参数进行第一次处理，通过“.”分隔符将propertyName分作两部分
        int delim = fullname.indexOf('.');
        if (delim > -1) {
            name = fullname.substring(0, delim);
            children = fullname.substring(delim + 1);
        } else {
            name = fullname;
            children = null;
        }
        indexedName = name;
        // 对name进行二次处理,去除“[...]”，并将方括号内的内容赋给index属性，如果name属性中包含“[]”的话
        delim = name.indexOf('[');
        if (delim > -1) {
            // 先取index内容再截取name更为方便些，要不然还需要一个临时变量，需要三步才能实现
            // 这里包含了一个前提：传入的参数如果有有[,则必然存在],并且是属性的最后一个字符
            index = name.substring(delim + 1, name.length() - 1);
            name = name.substring(0, delim);
        }
    }

    public String getName() {
        return name;
    }

    public String getIndex() {
        return index;
    }

    public String getIndexedName() {
        return indexedName;
    }

    public String getChildren() {
        return children;
    }

    public boolean hasNext() {
        return children != null;
    }

    public PropertyTokenizer next() {
        return new PropertyTokenizer(children);
    }

    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported, as it has no meaning in the context of properties.");
    }

    public Iterator<PropertyTokenizer> iterator() {
        return this;
    }
}