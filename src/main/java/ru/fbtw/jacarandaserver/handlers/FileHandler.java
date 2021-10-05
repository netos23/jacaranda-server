package ru.fbtw.jacarandaserver.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.jacarandaserver.io.FileReader;
import ru.fbtw.jacarandaserver.requests.exceptions.ResurseNotFoundException;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileHandler {
    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);

    private final String initialPath;
    private final ServerContext context;

    public FileHandler(ServerContext context) {
        this.initialPath = context.getPath();
        this.context = context;
    }

    public File getFile(String contextPath) {
        String absolutePath = initialPath + contextPath;
        return new File(absolutePath);
    }

    public byte[] handle(File srcFile) throws ResurseNotFoundException {
        try {
            if (srcFile.isDirectory()) {
                File templateFile = new File(context.getDirInfoTemplate());
                String template = FileReader.readFile(templateFile);

                StringBuilder builder = new StringBuilder();
                for (String filename : srcFile.list()) {
                    builder.append(String.format("<div>%s</div>\n", filename));
                }

                return String.format(template, srcFile.getPath(), builder)
                        .getBytes(StandardCharsets.UTF_8);
            } else {
                logger.debug("Reading file: {}", srcFile);
                return FileReader.readAllBytes(srcFile);
            }
        } catch (IOException e) {
            logger.error("File not found: {}", e.getMessage());
            e.printStackTrace();

            throw new ResurseNotFoundException("File not found", e);
        }
    }
}
