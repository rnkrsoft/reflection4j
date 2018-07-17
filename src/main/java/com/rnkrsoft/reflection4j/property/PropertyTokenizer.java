package com.rnkrsoft.reflection4j.property;

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
     * 接受格式
     * beanName.fieldName
     * beanName[x].fieldName
     * beanName.fieldName1.fieldName2
     * beanName[x].fieldName1[y].fieldName2
     * beanName[x].fieldName1.fieldName2
     *
     * @param fullName 属性名
     */
    public PropertyTokenizer(String fullName) {
        // 对参数进行第一次处理，通过“.”分隔符将propertyName分作两部分
        int dotIndex = fullName.indexOf('.');
        if (dotIndex > -1) {
            name = fullName.substring(0, dotIndex);
            children = fullName.substring(dotIndex + 1);
        } else {
            name = fullName;
            children = null;
        }
        indexedName = name;
        // 对name进行二次处理,去除“[...]”，并将方括号内的内容赋给index属性，如果name属性中包含“[]”的话
        dotIndex = name.indexOf('[');
        if (dotIndex > -1) {
            // 先取index内容再截取name更为方便些，要不然还需要一个临时变量，需要三步才能实现
            // 这里包含了一个前提：传入的参数如果有有[,则必然存在],并且是属性的最后一个字符
            index = name.substring(dotIndex + 1, name.length() - 1);
            name = name.substring(0, dotIndex);
        }
    }

    /**
     * 当前属性名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 当前属性序号
     *
     * @return
     */
    public String getIndex() {
        return index;
    }

    /**
     * 当前属性名和序号
     *
     * @return
     */
    public String getIndexedName() {
        return indexedName;
    }

    /**
     * 下一级属性
     *
     * @return
     */
    public String getChildren() {
        return children;
    }

    /**
     * 是否还有下一级属性
     *
     * @return
     */
    public boolean hasNext() {
        return children != null;
    }

    /**
     * 返回下一级属性迭代器
     *
     * @return
     */
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