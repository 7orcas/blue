package com.sevenorcas.blue.system.excel;

public class Column {
	private String name;
	private Integer width;
	private int clazz;
	
	public Column(String name, int width, int clazz) {
		this.name = name;
		this.width = width;
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}
	public Integer getWidth() {
		return width;
	}
	public int getCellClass() {
		return clazz;
	}
	
}
