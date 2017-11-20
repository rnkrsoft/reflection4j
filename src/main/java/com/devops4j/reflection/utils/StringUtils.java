package com.devops4j.reflection.utils;

/**
 * Created by devops4j on 2017/7/28.
 */
public class StringUtils {
    /**
     * 首字母小写
     *
     * @param value
     * @return
     */
    public static String toFirstLowerCase(String value) {
        if (value == null || value.length() < 1) {
            return value;
        }
        char[] chars = value.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    /**
     * 首字母大写
     *
     * @param value
     * @return
     */
    public static String toFirstUpperCase(String value) {
        if (value == null || value.length() < 1) {
            return value;
        }
        char[] chars = value.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * 将下划线风格的单词转换为驼峰命名
     *
     * @param value
     * @return
     */
    public static String toCamelCase(String value) {
        if (value == null) {
            return value;
        }
        String[] names = value.split("_");
        StringBuilder buffer = new StringBuilder(value.length());
        int idx = 0;
        for (String name : names) {
            if (idx == 0) {
                buffer.append(toFirstLowerCase(name));
            } else {
                buffer.append(toFirstUpperCase(name));
            }
            idx++;
        }
        return buffer.toString().trim();
    }
}
