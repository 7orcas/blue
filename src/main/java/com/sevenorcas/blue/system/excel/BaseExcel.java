package com.sevenorcas.blue.system.excel;

import java.util.ArrayList;
import java.util.List;

import com.sevenorcas.blue.system.lang.ent.LabelUtil;

/**
* Create an excel file 
* 
* [Licence]
* Created 05.09.22
* @author John Stewart
*/
public class BaseExcel implements ExcelI {
	
	private List<String> sheetNames;
	private LabelUtil labels;
	
	public BaseExcel (LabelUtil labels) {
		this.labels = labels;
		sheetNames = new ArrayList<>();
	}
	
	public BaseExcel addSheetName(String name) {
		sheetNames.add(name);
		return this;
	}

	public int getSheetCount() {
		return sheetNames.size();
	}

	public String getSheetName(int sheet) {
		return sheetNames.get(sheet);
	}
	
	public String getLabel(String langKey) {
		return labels.getLabel(langKey);
	}

	public Column addColumn(String langKey, int width, int clazz) {
		return new Column (getLabel(langKey), width, clazz);
	}
	
	public String getLanguage() {
		return labels.getLanguage();
	}

}
