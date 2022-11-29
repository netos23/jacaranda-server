package ru.fbtw.jacarandaserver.core.context.internalservlet.controller;

import ru.fbtw.jacarandaserver.core.context.internalservlet.service.InternalServletService;
import ru.fbtw.jacarandaserver.sage.app.annotation.Controller;
import ru.fbtw.jacarandaserver.sage.controller.mapping.annotation.GetMapping;
import ru.fbtw.jacarandaserver.sage.controller.mapping.annotation.PostMapping;
import ru.fbtw.jacarandaserver.sage.controller.mapping.annotation.RequestMapping;
import ru.fbtw.jacarandaserver.sage.controller.request.annotation.RequestParam;
import ru.fbtw.jacarandaserver.sage.view.Model;

import java.util.List;

@Controller
@RequestMapping("/")
public class InternalServletController {
	private final InternalServletService internalServletService;

	public InternalServletController(InternalServletService internalServletService) {
		this.internalServletService = internalServletService;
	}

	@GetMapping("/")
	public String indexPage() {
		return "index";
	}


}
