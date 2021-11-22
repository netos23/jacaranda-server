package ru.fbtw.jacarandaserver;

import ru.fbtw.jacarandaserver.configuration.core.Configurations;
import ru.fbtw.jacarandaserver.core.server.ServerConfiguration;

import java.io.IOException;

public class App {
	public static void main(String[] args) throws IOException {
		ServerConfiguration config = Configurations.readConfig(args, ServerConfiguration.class, null);
		//ServerConfiguration context = ServerConfiguration.DEFAULT_CONFIG;

	}
}
