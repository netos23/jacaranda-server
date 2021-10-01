package ru.fbtw.jacarandaserver.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.io.FileReader;
import ru.fbtw.jacarandaserver.requests.exceptions.ResurseNotFoundException;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

public class FileHandler {
    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);

    private final String initialPath;
    private final ServerContext context;

    public FileHandler(ServerContext context) {
        this.context = context;
        this.initialPath = context.getPath();
    }

    public File getFile(String contextPath) {
        String absolutePath = initialPath + contextPath;
        return new File(absolutePath);
    }

    public byte[] handle(File srcFile) throws ResurseNotFoundException {
        try {
            logger.debug("Reading file: {}", srcFile);
            String s = FileReader.readFile(srcFile);

            return s.getBytes(StandardCharsets.UTF_8);

        } catch (FileNotFoundException e) {
            logger.error("File not found: {}", e.getMessage());
            e.printStackTrace();

            throw new ResurseNotFoundException("File not found", e);
        }
    }

    public String getContentType(File file) {
        // todo: implement
        return "text/html";
    }
}
