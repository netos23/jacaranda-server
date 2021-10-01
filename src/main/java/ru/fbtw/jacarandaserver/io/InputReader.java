package ru.fbtw.jacarandaserver.io;

import java.io.*;
import java.util.StringTokenizer;

public class InputReader {
    public final BufferedReader reader;
    public StringTokenizer tokenizer;

    public InputReader(BufferedReader reader) {
        this.reader = reader;
        tokenizer = null;
    }

    public InputReader(String input) {
        this(new BufferedReader(new StringReader(input)));
    }

    public InputReader(InputStream is) {
        this(new BufferedReader(new InputStreamReader(is)));
    }

    public int readBuffer(char[] buffer) throws IOException {
        return reader.read(buffer);
    }

    public String next() throws IOException {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            tokenizer = new StringTokenizer(reader.readLine());
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
