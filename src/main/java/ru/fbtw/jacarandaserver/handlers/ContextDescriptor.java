package ru.fbtw.jacarandaserver.handlers;

import java.io.File;

public class ContextDescriptor {
    private File initialPath;

    public ContextDescriptor(String initialPath) {
        this.initialPath = new File(initialPath);
    }


}
