package ru.fbtw.util.packegescanner;

import java.nio.file.Path;
import java.util.function.Consumer;

@Deprecated
public interface ClassConsumer extends Consumer<Path> {
	@Override
	default void accept(Path path) {
		Class<?> clazz = ClassExtractors.fromPath(path);
		consume(clazz);
	}

	void consume(Class<?> clazz);


}
