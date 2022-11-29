package ru.fbtw.jacarandaserver.sage.controller.filter;

import ru.fbtw.jacarandaserver.sage.bean.annotation.Component;
import ru.fbtw.jacarandaserver.sage.controller.filter.afterfilters.AfterRequestFilter;

import java.util.List;
@Component
public class AfterRequestFilterChain extends GenericFilterChain<AfterRequestFilter> {
	public AfterRequestFilterChain(List<AfterRequestFilter> filters) {
		super(filters);
	}
}
