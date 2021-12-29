package ru.fbtw.jacarandaserver.sage.view;

import ru.fbtw.jacarandaserver.sage.bean.annotation.Component;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Service;

import java.util.function.Function;


public class JsonRestPresenter extends AbstractRestPresenter<Object> {
	protected JsonRestPresenter(Function<Object, String> serializerDelegate) {
		super(serializerDelegate);
	}
}
