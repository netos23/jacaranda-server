package ru.fbtw.jacarandaserver.sage.model;


public class GenericRepository <T,ID>{
	private final DataSource dataSource;

	public GenericRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
