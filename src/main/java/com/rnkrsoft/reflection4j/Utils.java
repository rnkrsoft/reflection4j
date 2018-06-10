package com.rnkrsoft.reflection4j;

/**
 * Created by rnkrsoft on 2017/7/28.
 */
public class Utils {
    public static final String CLASSPATH_ALL_URL_PREFIX = "classpath*:";


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
    public static String convertPackageToPath(String path) {
        String filePath = path;
        String suffix = "";
        int fileNamePos = path.lastIndexOf(".");
        if (fileNamePos > -1) {
            filePath = path.substring(0, fileNamePos).replaceAll("\\.", "\\/");
            suffix = path.substring(fileNamePos + 1);
        }
        String file = filePath + "." + suffix;
        return file;
    }

    public static String deleteClassPathStar(String path) {
        String temp = path;
        int classpathPos = temp.indexOf(CLASSPATH_ALL_URL_PREFIX);
        if (classpathPos > -1) {
            temp = path.substring(classpathPos + CLASSPATH_ALL_URL_PREFIX.length());
        }
        return temp;
    }
    public static String convertPathToPackage(String path) {
        String filePath = path;
        String suffix = "";
        int fileNamePos = path.lastIndexOf(".");
        if (fileNamePos > -1) {
            filePath = path.substring(0, fileNamePos).replaceAll("/", ".");
            suffix = path.substring(fileNamePos + 1);
        }
        String file = filePath + "." + suffix;
        return file;
    }
}
