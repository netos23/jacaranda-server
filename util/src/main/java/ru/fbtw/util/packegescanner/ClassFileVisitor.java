package ru.fbtw.util.packegescanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Consumer;

public class ClassFileVisitor extends SimpleFileVisitor<Path> {
	public static final String CLASS_FILE_SUFFIX = ".class";
	private static final String PACKAGE_INFO_FILE_NAME = "package-info.class";
	private static final String MODULE_INFO_FILE_NAME = "module-info.class";

	private static final Logger logger = LoggerFactory.getLogger(ClassFileVisitor.class);

	private final Consumer<Path> classFileConsumer;

	private boolean isNotPackageInfo(Path path) {
		return !path.endsWith(PACKAGE_INFO_FILE_NAME);
	}

	private static boolean isNotModuleInfo(Path path) {
		return !path.endsWith(MODULE_INFO_FILE_NAME);
	}

	private static boolean isClassFile(Path file) {
		return file.getFileName().toString().endsWith(CLASS_FILE_SUFFIX);
	}

	public ClassFileVisitor(Consumer<Path> classFileConsumer) {
		this.classFileConsumer = classFileConsumer;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
		if (isNotPackageInfo(file) && isNotModuleInfo(file) && isClassFile(file)) {
			this.classFileConsumer.accept(file);
		}

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException ex) {
		logger.warn("I/O error visiting file: " + file);
		ex.printStackTrace();
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException ex) {
		if (ex != null) {
			logger.warn("I/O error visiting directory: " + dir);
			ex.printStackTrace();
		}

		return FileVisitResult.CONTINUE;
	}
}
