package com.sevenorcas.blue.system.excel;

/**
* Excel sheet  
* 
* [Licence]
* Created 05.09.22
* @author John Stewart
*/
public class Sheet {
	private String langKey;
	private String label;
	private Integer sheet;
	
	public Sheet (Integer sheet, String langKey, String label) {
		this.langKey = langKey;
		this.label = label;
		this.sheet = sheet;
	}
	
	
	public String getLangKey() {
		return langKey;
	}

	public String getLabel() {
		return label;
	}

	public Integer getIndex() {
		return sheet;
	}
	
	
}
