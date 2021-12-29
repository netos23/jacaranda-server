package ru.fbtw.jacarandaserver.sage.view;

public interface ViewPresenter<T> {
	byte[] present(T content);
}
