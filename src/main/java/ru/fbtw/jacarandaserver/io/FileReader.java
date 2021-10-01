package ru.fbtw.jacarandaserver.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    public static String readAllStrings(Scanner in) {
        StringBuilder builder = new StringBuilder();
        boolean eof = in.hasNext();

        while (eof) {
            builder.append(in.nextLine());
            eof = in.hasNext();
            if (eof) {
                builder.append('\n');
            }
        }
        return builder.toString();
    }

    public static String readFile(File src) throws FileNotFoundException {
        Scanner in = new Scanner(src);
        return readAllStrings(in);
    }
}
