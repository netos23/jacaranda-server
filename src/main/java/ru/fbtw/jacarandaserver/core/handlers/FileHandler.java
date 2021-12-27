package ru.fbtw.jacarandaserver.core.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fbtw.util.io.IOUtils;
import ru.fbtw.jacarandaserver.api.requests.exceptions.ResourceNotFoundException;
import ru.fbtw.jacarandaserver.core.context.configuration.ServerConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileHandler {
    private static final Logger logger = LoggerFactory.getLogger(FileHandler.class);

    private final String initialPath;
    private final ServerConfiguration context;

    public FileHandler(ServerConfiguration context) {
        this.initialPath = context.getPath();
        this.context = context;
    }

    public File getFile(String contextPath) {
        String absolutePath = initialPath + contextPath;
        return new File(absolutePath);
    }

    public byte[] handle(File srcFile) throws ResourceNotFoundException {
        try {
            if (srcFile.isDirectory()) {
                File templateFile = new File(context.getDirInfoTemplate());
                String template = IOUtils.readAllStrings(templateFile);

                StringBuilder builder = new StringBuilder();
                // todo: check null
                for (String filename : srcFile.list()) {
                    builder.append(String.format("<div>%s</div>\n", filename));
                }

                return String.format(template, srcFile.getPath(), builder)
                        .getBytes(StandardCharsets.UTF_8);
            } else {
                logger.debug("Reading file: {}", srcFile);
                return IOUtils.readAllBytes(srcFile);
            }
        } catch (IOException e) {
            logger.error("File not found: {}", e.getMessage());
            e.printStackTrace();

            throw new ResourceNotFoundException("File not found", e);
        }
    }
}
