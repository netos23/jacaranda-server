package ru.fbtw.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
	public static<T> List<T> mergeLists(List<T> oldList, List<T> newList) {
		List<T> merged = new ArrayList<>();
		merged.addAll(oldList);
		merged.addAll(newList);
		return merged;
	}
}
