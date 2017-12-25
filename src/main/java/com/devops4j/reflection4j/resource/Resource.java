package com.devops4j.reflection4j.resource;

import com.devops4j.logtrace4j.ErrorContextFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import static com.devops4j.reflection4j.Utils.*;

/**
 * Created by devops4j on 2017/12/2.
 */
public class Resource {
    public static final int EOF = -1;
    protected ClassLoader classLoader;

    public Resource(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Resource() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public URL url(String src) {
        return url(src, this.classLoader == null ? this.classLoader : Thread.currentThread().getContextClassLoader());
    }

    public URL url(String src, ClassLoader classLoader) {
        if (!src.startsWith(CLASSPATH_ALL_URL_PREFIX)) {
            ErrorContextFactory.instance().message("文件'{}'不是有效的classpath*资源访问", src).throwError();
            return null;
        }
        String temp = deleteClassPathStar(src);
        String file = convertPackageToPath(temp);
        URL url = classLoader.getResource(file);
        if (url == null) {
            ErrorContextFactory.instance().message("文件'{}'不存在", src).throwError();
            return null;
        }
        return url;
    }

    public byte[] toByteArray(final URLConnection urlConn) throws IOException {
        final InputStream inputStream = urlConn.getInputStream();
        try {
            return toByteArray(inputStream);
        } finally {
            inputStream.close();
        }
    }

    public byte[] toByteArray(final InputStream input) throws IOException {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

    public int copy(final InputStream input, final OutputStream output) throws IOException {
        final long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    public long copy(final InputStream input, final OutputStream output, final int bufferSize)
            throws IOException {
        return copyLarge(input, output, new byte[bufferSize]);
    }

    public long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer)
            throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public long copyLarge(final InputStream input, final OutputStream output)
            throws IOException {
        return copy(input, output, 1024 * 4);
    }

    public byte[] readFileToByteArray(String src, ClassLoader classLoader) throws IOException {
        URL url = url(src, classLoader == null ? this.classLoader : classLoader);
        InputStream is = url.openStream();
        try {
            byte[] data = toByteArray(is);
            return data;
        } finally {
            closeQuietly(is);
        }
    }

    public byte[] readFileToByteArray(String src) throws IOException {
        return readFileToByteArray(src, this.classLoader);
    }

    public String readFileToString(String src, String encoding, ClassLoader classLoader) throws IOException {
        return new String(readFileToByteArray(src, classLoader == null ? this.classLoader : classLoader), encoding);
    }

    public String readFileToString(String src, String encoding) throws IOException {
        return readFileToString(src, encoding, this.classLoader);
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