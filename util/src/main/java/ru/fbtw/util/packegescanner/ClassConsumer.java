package ru.fbtw.util.packegescanner;

import java.nio.file.Path;
import java.util.function.Consumer;

public interface ClassConsumer extends Consumer<Path> {
	@Override
	default void accept(Path path) {
		Class<?> clazz = ClassExtractors.fromPath(path);
		accept(clazz);
	}

	void accept(Class<?> clazz);


}
