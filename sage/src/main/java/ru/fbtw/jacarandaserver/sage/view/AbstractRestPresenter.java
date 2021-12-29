package ru.fbtw.jacarandaserver.sage.view;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public abstract class AbstractRestPresenter<T> implements ViewPresenter<T> {

	protected abstract String serialize(T content);

	@Override
	public byte[] present(T content) {
		String serializedContent = serialize(content);
		return serializedContent.getBytes(StandardCharsets.UTF_8);
	}
}
