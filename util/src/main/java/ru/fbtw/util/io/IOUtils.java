package ru.fbtw.util.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class IOUtils {

	public static String readAllStrings(File file) throws IOException {
		byte[] bytes = readAllBytes(file);
		return new String(bytes);
	}


	public static byte[] readAllBytes(File file) throws IOException {
		return readAllBytes(Files.newInputStream(file.toPath()));
	}

	public static byte[] readAllBytes(InputStream is) throws IOException {

		byte[] bytes = new byte[is.available()];
		int read = is.read(bytes, 0, bytes.length);
		is.close();

		return bytes;
	}


}
