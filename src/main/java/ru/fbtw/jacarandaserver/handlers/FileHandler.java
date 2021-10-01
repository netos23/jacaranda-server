package ru.fbtw.jacarandaserver.handlers;

import ru.fbtw.jacarandaserver.io.FileReader;
import ru.fbtw.jacarandaserver.requests.exceptions.ResurseNotFoundException;
import ru.fbtw.jacarandaserver.server.ServerContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

public class FileHandler {
    private final String initialPath;
    private final ServerContext context;

    public FileHandler(ServerContext context) {
        this.context = context;
        this.initialPath = context.getPath();
    }

    public File getFile(String contextPath){
        String absolutePath = initialPath + contextPath;
        return new File(absolutePath);
    }

    public byte[] handle(File srcFile) throws ResurseNotFoundException {
        try {
            String s = FileReader.readFile(srcFile);
            return s.getBytes(StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            // todo: log there
            e.printStackTrace();
            throw new ResurseNotFoundException("File not found", e);
        }
    }

    public String getContentType(File file) {
        return "text/html";
    }
}
