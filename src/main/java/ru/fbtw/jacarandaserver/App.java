package ru.fbtw.jacarandaserver;

import ru.fbtw.jacarandaserver.configuration.core.Configurations;
import ru.fbtw.jacarandaserver.core.bootloader.ServletBootloader;
import ru.fbtw.jacarandaserver.core.bootloader.ServletBootstrap;
import ru.fbtw.jacarandaserver.core.server.HttpServer;
import ru.fbtw.jacarandaserver.core.server.ServerConfiguration;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class App {
	public static void main(String[] args) throws IOException {
		String[] split = "/pi/".split("/");
		System.out.println(split.length);
//		ServerConfiguration config = Configurations.readConfig(args, ServerConfiguration.class, null);
//		List<String> packages = Collections.singletonList("ru.fbtw.jacarandaserver.core.servlet");
//		new ServletBootstrap(
//				new ServletBootloader(null, packages), "./servlets/", 5).run();
		System.out.println("tu");
//		HttpServer server = new HttpServer(config);
//		server.start();
	}
}
