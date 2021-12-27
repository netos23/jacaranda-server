package ru.fbtw.util.pathtree;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {
	private int index;
	private final T[] array;

	public ArrayIterator(T[] array) {
		this(0, array);
	}

	public ArrayIterator(int index, T[] array) {
		this.index = index;
		this.array = array;
	}

	@Override
	public boolean hasNext() {
		return index < array.length;
	}

	@Override
	public T next() {
		return array[index++];
	}
}
