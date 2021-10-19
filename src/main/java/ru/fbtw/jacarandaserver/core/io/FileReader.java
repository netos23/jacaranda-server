package ru.fbtw.jacarandaserver.core.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileReader {

    public static String readAllStrings(File file) throws IOException {
        byte[] bytes = readAllBytes(file);
        return new String(bytes);
    }



    public static byte[] readAllBytes(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[fileInputStream.available()];
        int read = fileInputStream.read(bytes, 0, bytes.length);
        fileInputStream.close();
        return bytes;
    }


}
