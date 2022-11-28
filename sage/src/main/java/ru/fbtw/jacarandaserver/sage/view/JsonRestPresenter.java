package ru.fbtw.jacarandaserver.sage.view;

import com.google.gson.Gson;


public class JsonRestPresenter extends AbstractRestPresenter<Object> {

	private final Gson gson;

	public JsonRestPresenter() {
		gson = new Gson();
	}

	@Override
	protected String serialize(Object content) {
		return gson.toJson(content);
	}
}
