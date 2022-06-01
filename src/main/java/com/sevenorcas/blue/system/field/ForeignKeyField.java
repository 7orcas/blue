package com.sevenorcas.blue.system.field;

/**
 * Created 20 May 2022
 * 
 * Utility Class to help the encode class.
 * Stores Foreign Key Field info.
 * 
 * There is no Unit Test for this class as it is covered in the Encode Unit Test. 
 * 
 * [Licence]
 */

public class ForeignKeyField {

	private String schema;
	private String table;
	private String column;
	private String field;
	private Object value;
	
	public ForeignKeyField() {
	}

	public ForeignKeyField field(String s) {
		field = s;
		return this;
	}
	
	public ForeignKeyField value(Object o) {
		value = o;
		return this;
	}
	
	public ForeignKeyField schema(String s) {
		schema = s;
		return this;
	}

	public ForeignKeyField table(String s) {
		table = s;
		return this;
	}
	
	public ForeignKeyField column(String s) {
		column = s;
		return this;
	}
	
	public String getSchema() {
		return schema;
	}

	public String getTable() {
		return table;
	}

	public String getColumn() {
		return column;
	}

	public String getField() {
		return field;
	}

	public Object getValue() {
		return value;
	}
	
}
