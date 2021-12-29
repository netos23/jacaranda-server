package ru.fbtw.jacarandaserver.sage.view;

import java.util.HashMap;
import java.util.Map;

public class Model {
	final Map<String, Object> internalModel;

	public Model() {
		internalModel = new HashMap<>();
	}

	public void addAttribute(String attribute, Object value) {
		internalModel.put(attribute, value);
	}

	public Map<String, Object> getInternalModel() {
		return internalModel;
	}

}
