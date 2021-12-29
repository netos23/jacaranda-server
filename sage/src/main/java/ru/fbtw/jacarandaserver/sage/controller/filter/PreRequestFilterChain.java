package ru.fbtw.jacarandaserver.sage.controller.filter;

import ru.fbtw.jacarandaserver.sage.bean.annotation.Component;
import ru.fbtw.jacarandaserver.sage.controller.filter.afterfilters.PreRequestFilter;

import java.util.List;

@Component
public class PreRequestFilterChain extends GenericFilterChain<PreRequestFilter>{

	public PreRequestFilterChain(List<PreRequestFilter> filters) {
		super(filters);
	}


}
