package ru.fbtw.controller;

import ru.fbtw.configuration.AsciiConverterMvcConfiguration;
import ru.fbtw.jacarandaserver.sage.bean.annotation.Controller;
import ru.fbtw.jacarandaserver.sage.controller.mapping.GetMapping;
import ru.fbtw.jacarandaserver.sage.controller.mapping.PostMapping;
import ru.fbtw.jacarandaserver.sage.controller.mapping.RequestMapping;
import ru.fbtw.jacarandaserver.sage.controller.request.annotation.RequestParam;
import ru.fbtw.jacarandaserver.sage.view.Model;
import ru.fbtw.service.ConverterService;

import javax.lang.model.element.Name;
import java.util.List;

@Controller
@RequestMapping("/ascii-converter")
public class AsciiConverterController {
	private final ConverterService converterService;

	public AsciiConverterController(ConverterService converterService) {
		this.converterService = converterService;
	}

	@GetMapping("/")
	public String indexPage() {
		return "index";
	}

	@PostMapping("/")
	public String convertImage(@RequestParam String base64Image, Model model) {
		String asciiImage = converterService.convert(base64Image);
		model.addAttribute("image", asciiImage);
		return "imageView";
	}
}
