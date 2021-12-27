package ru.fbtw.jacarandaserver;



import ru.fbtw.configuration.core.Configurations;
import ru.fbtw.jacarandaserver.core.context.configuration.ServerConfiguration;
import ru.fbtw.jacarandaserver.core.server.HttpServer;

import java.io.IOException;

public class App {
	public static void main(String[] args) throws IOException {
		String[] split = "/pi/".split("/");
		System.out.println(split.length);
		ServerConfiguration config = Configurations.readConfig(args, ServerConfiguration.class, null);

		HttpServer server = new HttpServer(config);
		server.start();
	}
}
