package ru.fbtw.jacarandaserver.sage.exception;


import ru.fbtw.jacarandaserver.api.requests.HttpResponse;
import ru.fbtw.jacarandaserver.api.requests.enums.ContentType;
import ru.fbtw.jacarandaserver.api.requests.enums.HttpHeader;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Component;
import ru.fbtw.jacarandaserver.sage.view.DataModelWithView;
import ru.fbtw.jacarandaserver.sage.view.Model;
import ru.fbtw.jacarandaserver.sage.view.MvcViewPresenter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GenericExceptionHandlerRegistry {
	private final Map<Class<? extends Exception>, GenericExceptionHandlerBean> exceptionHandlerMap;
	private final MvcViewPresenter viewPresenter;

	public GenericExceptionHandlerRegistry(
			List<GenericExceptionHandlerBean> exceptionHandlers,
			MvcViewPresenter viewPresenter
	) {
		this.exceptionHandlerMap = exceptionHandlers.stream()
				.collect(Collectors.
						<GenericExceptionHandlerBean,
								Class<? extends Exception>,
								GenericExceptionHandlerBean>toMap(
						GenericExceptionHandlerBean::getExceptionType,
						exceptionHandler -> exceptionHandler
				));
		this.viewPresenter = viewPresenter;
	}

	public void handle(Exception ex, HttpResponse.HttpResponseBuilder responseBuilder) {
		GenericExceptionHandlerBean genericExceptionHandlerBean
				= exceptionHandlerMap.get(ex.getClass());
		ExceptionResponseEntity exceptionResponse = genericExceptionHandlerBean.handle(ex);

		Model model = new Model();
		model.addAttribute("exceptionEntity", exceptionResponse);
		DataModelWithView modelWithView = new DataModelWithView("internalError", model);

		byte[] body = viewPresenter.present(modelWithView);
		responseBuilder.setStatus(exceptionResponse.getStatus());
		responseBuilder.setBody(body);
		responseBuilder.addHeader(HttpHeader.CONTENT_TYPE.getHeaderName(), ContentType.HTML.getValue());
	}
}
