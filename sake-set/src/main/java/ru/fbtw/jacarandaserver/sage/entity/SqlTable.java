package ru.fbtw.jacarandaserver.sage.entity;

import java.util.List;

public class SqlTable {
	final String tableName;
	final List<SqlColumn> columns;

	public SqlTable(String tableName, List<SqlColumn> columns) {
		this.tableName = tableName;
		this.columns = columns;
	}
}
