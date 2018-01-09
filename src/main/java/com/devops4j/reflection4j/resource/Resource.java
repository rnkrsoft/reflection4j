package com.devops4j.reflection4j.resource;


import com.devops4j.logtrace4j.ErrorContextFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.devops4j.reflection4j.Utils.*;

/**
 * Created by devops4j on 2017/12/2.
 */
public class Resource {

    public URL url(String src) {
        return url(src, Thread.currentThread().getContextClassLoader());
    }

    public URL url(String src, ClassLoader classLoader) {
        if (!src.startsWith(CLASSPATH_ALL_URL_PREFIX)) {
            throw ErrorContextFactory.instance().message("文件'{}'不是有效的classpath*资源访问", src).runtimeException();
        }
        String temp = deleteClassPathStar(src);
        String file = convertPackageToPath(temp);
        URL url = classLoader.getResource(file);
        if (url == null) {
            throw ErrorContextFactory.instance().message("文件'{}'不存在", src).runtimeException();
        }
        return url;
    }

    public byte[] readFileToByteArray(String src) throws IOException {
        InputStream is = url(src, Thread.currentThread().getContextClassLoader()).openStream();
        try {
            byte[] data = readFully(is, is.available());
            return data;
        } finally {
            closeQuietly(is);
        }
    }

    public String readFileToString(String src, String encoding) throws IOException {
        return new String(readFileToByteArray(src), encoding);
    }

    byte[] readFully(InputStream is, int count) throws IOException {
        byte[] data = new byte[count];
        is.read(data);
        return data;
    }

    void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                //nothing
            }
        }
    }
}