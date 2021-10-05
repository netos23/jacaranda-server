package ru.fbtw.jacarandaserver.io;

import java.io.FileReader;
import java.io.*;
import java.util.StringTokenizer;

public class InputReader {
    private final BufferedReader reader;
    private StringTokenizer tokenizer;
    private boolean hasTimeOut;

    public InputReader(BufferedReader reader, boolean hasTimeOut) {
        this.reader = reader;
        this.hasTimeOut = hasTimeOut;
        tokenizer = null;
    }

    public InputReader(BufferedReader reader) {
        this(reader, false);
    }

    public InputReader(String input) {
        this(new BufferedReader(new StringReader(input)), true);
    }

    public InputReader(InputStream is) {
        this(new BufferedReader(new InputStreamReader(is)), false);
    }

    public InputReader(File file) throws FileNotFoundException {
        this(new BufferedReader(new FileReader(file)), true);
    }

    public int readBuffer(char[] buffer) throws IOException {
        return reader.read(buffer);
    }

    public String next() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine());
            } catch (NullPointerException ex) {
                if (hasTimeOut) {
                    ex.printStackTrace();
                    throw new IOException("File is empty or end of file has been reached");
                } else {
                    // todo: log there
                }
            }

        }
        return tokenizer.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    public long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    public double nextDouble() throws IOException {
        return Double.parseDouble(next());
    }

    public String nextLine() throws IOException {
        if (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tokenizer = null;
            return reader.readLine();
        } else {
            return tokenizer.nextToken("").trim();
        }
    }

}
