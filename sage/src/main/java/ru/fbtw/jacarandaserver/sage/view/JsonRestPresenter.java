package ru.fbtw.jacarandaserver.sage.view;

import com.google.gson.Gson;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Component;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Service;

import java.util.function.Function;


@Component
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
