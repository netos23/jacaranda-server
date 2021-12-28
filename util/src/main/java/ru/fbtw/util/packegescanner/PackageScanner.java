package ru.fbtw.util.packegescanner;

import ru.fbtw.util.IterableAdapter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.function.Consumer;

public class PackageScanner {
	public static final String ROOT = ".";
	private final ClassLoader classLoader;

	public PackageScanner(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public void scan(Consumer<Class<?>> classConsumer) throws IOException {
		scan(classConsumer, ROOT);
	}

	public void scan(Consumer<Class<?>> clazzConsumer, String targetPackage) throws IOException {
		File file = new File(targetPackage);
		ClassLoader classLo = new URLClassLoader(
				new URL[]{file.toURI().toURL()},
				this.getClass().getClassLoader()
		);

		Enumeration<URL> resources = classLo.getResources("classpath:");
		Iterable<URL> resItr = new IterableAdapter<>(resources);

		for(URL url : resItr){
			System.out.println(url);
		}
	}
}
