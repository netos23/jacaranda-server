package ru.fbtw.util;

import java.util.Enumeration;
import java.util.Iterator;

public class IterableAdapter<T> implements Iterable<T> {
	private final Enumeration<T> enumeration;

	public IterableAdapter(Enumeration<T> enumeration) {
		this.enumeration = enumeration;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			@Override
			public boolean hasNext() {
				return enumeration.hasMoreElements();
			}

			@Override
			public T next() {
				return enumeration.nextElement();
			}
		};
	}



}
