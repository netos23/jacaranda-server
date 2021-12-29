package ru.fbtw.jacarandaserver.sage.view;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public abstract class AbstractRestPresenter<T> implements ViewPresenter<T> {
	private final Function<T, String> serializerDelegate;

	protected AbstractRestPresenter(Function<T, String> serializerDelegate) {
		this.serializerDelegate = serializerDelegate;
	}

	@Override
	public byte[] present(T content) {
		String serializedContent = serializerDelegate.apply(content);
		return serializedContent.getBytes(StandardCharsets.UTF_8);
	}
}
