package ru.fbtw.util.packegescanner;

import java.nio.file.Path;

public class ClassExtractors  {

	public static Class<?> fromPath(Path path) {
		int offset = ClassFileVisitor.CLASS_FILE_SUFFIX.length();
		String filename = path.getFileName().toString();
		String classname = filename.substring(0, filename.length() - offset);

		Class<?> clazz = null;
		try {
			clazz = Class.forName(classname);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return clazz;
	}
}
