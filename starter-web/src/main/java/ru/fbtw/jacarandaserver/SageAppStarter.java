package ru.fbtw.jacarandaserver;

import ru.fbtw.configuration.core.Configurations;
import ru.fbtw.jacarandaserver.core.context.configuration.ServerConfiguration;
import ru.fbtw.jacarandaserver.core.server.HttpServer;
import ru.fbtw.jacarandaserver.sage.app.ServletFacade;

import java.io.IOException;

public class SageAppStarter {
	public static final String ROOT_PATH = "/";

	public static void start(String[] args) throws IOException {
		ServerConfiguration config = Configurations.readConfig(args, ServerConfiguration.class, null);

		HttpServer server = new HttpServer(config);
		ServletFacade facade = new ServletFacade();
		server.addServlet(ROOT_PATH, facade);
		server.start();
	}
}
