package ru.fbtw.jacarandaserver.sage.view;

public class DataModelWithView {
	private final String view;
	private final Model model;

	public DataModelWithView(String view, Model model) {
		this.view = view;
		this.model = model;
	}

	public String getView() {
		return view;
	}

	public Model getModel() {
		return model;
	}
}
