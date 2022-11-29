package ru.fbtw.controller;

import ru.fbtw.jacarandaserver.sage.app.annotation.Controller;
import ru.fbtw.jacarandaserver.sage.controller.mapping.annotation.GetMapping;
import ru.fbtw.jacarandaserver.sage.controller.mapping.annotation.PostMapping;
import ru.fbtw.jacarandaserver.sage.controller.mapping.annotation.RequestMapping;
import ru.fbtw.jacarandaserver.sage.controller.request.annotation.RequestParam;
import ru.fbtw.jacarandaserver.sage.view.Model;
import ru.fbtw.service.ConverterService;

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
	public String convertImage(@RequestParam(name = "base64Image") List<String> base64Image, Model model) {
		String asciiImage = converterService.convert(base64Image.get(0));
		model.addAttribute("image", asciiImage);
		return "imageView";
	}
}
