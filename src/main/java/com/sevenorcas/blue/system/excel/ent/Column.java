package com.sevenorcas.blue.system.excel.ent;

public class Column {
	private int index;
	private String label;
	private String langKey;
	private Integer width;
	private Integer clazz;
	
	public Column(int index, String langKey, String label, Integer width, Integer clazz) {
		this.index = index;
		this.langKey = langKey;
		this.label = label;
		this.width = width;
		this.clazz = clazz;
	}

	public int getIndex() {
		return index;
	}
	public Column setIndex(int index) {
		this.index = index;
		return this;
	}
	public String getLangKey() {
		return langKey;
	}
	public String getLabel() {
		return label;
	}
	public Integer getWidth() {
		return width;
	}
	public Integer getCellClass() {
		return clazz;
	}
	
}
