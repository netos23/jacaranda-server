package ru.fbtw.jacarandaserver.sage.controller.filter;

import ru.fbtw.jacarandaserver.sage.controller.filter.prefilters.PreRequestFilter;

import java.util.List;

public class PreRequestFilterChain extends GenericFilterChain<PreRequestFilter>{

	public PreRequestFilterChain(List<PreRequestFilter> filters) {
		super(filters);
	}


}
