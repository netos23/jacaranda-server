package ru.fbtw.jacarandaserver.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {
    private static final int BUFFER_SIZE = 100;

    public static String readAllStrings(BufferedReader in) throws IOException {
        StringBuilder builder = new StringBuilder();
        char[] buf = new char[BUFFER_SIZE];
        int readBytes;
        while ((readBytes = in.read(buf)) != -1) {
            builder.append(buf, 0, readBytes);
        }
        return builder.toString();
    }

    public static byte[] readAllBytes(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[fileInputStream.available()];
        int read = fileInputStream.read(bytes, 0, bytes.length);
        return bytes;
    }

    public static String readFile(File src) throws IOException {
        BufferedReader reader = new BufferedReader(new java.io.FileReader(src));
        return readAllStrings(reader);
    }
}
