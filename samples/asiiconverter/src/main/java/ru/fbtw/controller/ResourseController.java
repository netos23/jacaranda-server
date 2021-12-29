package ru.fbtw.controller;

import ru.fbtw.jacarandaserver.sage.bean.annotation.RestController;
import ru.fbtw.jacarandaserver.sage.controller.mapping.GetMapping;

@RestController
public class ResourseController {
	@GetMapping("/t")
	public String test(){
		return "test";
	}
}
