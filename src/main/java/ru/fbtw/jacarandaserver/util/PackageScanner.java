package ru.fbtw.jacarandaserver.util;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class PackageScanner {

	public static final String SUFFIX = ".class";
	private final Set<String> packages;
	private final Map<String, List<Class<?>>> loadedClasses;
	private final ClassLoader loader;

	public PackageScanner(ClassLoader loader) {
		this.loader = loader;
		packages = new HashSet<>();
		loadedClasses = new HashMap<>();
	}

	public void scanPackage(String pack) {
		if (packages.contains(pack)) {
			return;
		}
		try {
			String canonicalPath = pack.replace('.', '/');
			Enumeration<URL> resources = loader.getResources(canonicalPath);
			Iterable<URL> resItr = new EnumerationIterable<>(resources);
			List<File> dirs = new ArrayList<>();

			resItr.forEach(url -> {
				try {
					File file = new File(url.toURI().getPath());
					dirs.add(file);
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
			});

			packages.add(pack);
			List<Class<?>> classes = new ArrayList<>();
			loadedClasses.put(pack, classes);
			dirs.stream()
					.map(f -> getClasses(f, pack))
					.forEach(classes::addAll);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void scanPackages(List<String> packages) {
		packages.forEach(this::scanPackage);
	}

	public List<Class<?>> getMarkedClasses(Class<? extends Annotation> annotation, String pack) {
		List<Class<?>> classList = loadedClasses.get(pack);

		if (classList == null) {
			return Collections.emptyList();
		}

		return classList.stream()
				.filter(c -> Reflections.hasAnnotation(annotation, c))
				.collect(Collectors.toList());
	}

	public List<Class<?>> getMarkedClasses(Class<? extends Annotation> annotation, Collection<String> pack) {
		return pack.stream()
				.flatMap(d -> getMarkedClasses(annotation, d).stream())
				.collect(Collectors.toList());
	}

	public List<Class<?>> getMarkedClasses(Class<? extends Annotation> annotation) {
		return getMarkedClasses(annotation, packages);
	}


	private List<Class<?>> getClasses(File dir, String pack) {
		if (!dir.exists()) {
			return Collections.emptyList();
		}

		File[] children = dir.listFiles();
		if (children == null) {
			return Collections.emptyList();
		}

		List<Class<?>> classes = new ArrayList<>();
		for (File file : children) {
			if (file.isDirectory()) {
				classes.addAll(getClasses(file, pack + "." + file.getName()));
			} else if (file.getName().endsWith(SUFFIX)) {
				try {
					String className = pack + '.' + file.getName().substring(0, file.getName().length() - 6);
					Class<?> clazz = Class.forName(className);
					classes.add(clazz);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		return classes;
	}
}
