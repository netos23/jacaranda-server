package ru.fbtw.jacarandaserver.sage.view;

import ru.fbtw.jacarandaserver.sage.view.exception.ViewNotFoundException;
import ru.fbtw.util.io.IOUtils;

import java.io.File;
import java.io.IOException;

public class FilePresenter implements ViewPresenter<String> {

	@Override
	public byte[] present(String content) {
		File file = new File(content);
		try {
			return IOUtils.readAllBytes(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ViewNotFoundException(e);
		}
	}
}
