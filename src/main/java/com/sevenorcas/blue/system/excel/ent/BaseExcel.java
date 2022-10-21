package com.sevenorcas.blue.system.excel.ent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sevenorcas.blue.system.base.BaseUtil;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;

/**
* Create an excel file 
* 
* [Licence]
* Created 05.09.22
* @author John Stewart
*/
public abstract class BaseExcel extends BaseUtil implements ExcelI {
	
	protected List<Sheet> sheets;
	protected Map<Integer, List<Column>> columns;
	protected UtilLabel labels;
	protected ExcelImport excel;
	protected Boolean isInvalid;
	protected Boolean isChanged;
	protected Boolean isImportComment;
	
	
	public BaseExcel (UtilLabel labels) {
		this.labels = labels;
		initialise (labels);
	}
	
	public BaseExcel (UtilLabel labels, ExcelImport excel) {
		this.excel = excel;
		initialise (labels);
	}
	
	private void initialise (UtilLabel labels) {
		this.labels = labels;
		sheets = new ArrayList<>();
		columns = new HashMap<>();
	}

	
	public BaseExcel addSheet(Integer sheet, String langKey) throws Exception {
		for (Sheet s : sheets) {
			if (s.getLangKey().equals(langKey)) {
				throw new RedException("errunk", "Sheet name is duplicated: " + getLabel(langKey, true));
			}
		}
		
		sheets.add(new Sheet(sheet, langKey, getLabel(langKey, true)));
		return this;
	}
	
	public int getSheetCount() {
		return sheets.size();
	}

	public Sheet getSheet(int sheet) {
		return sheets.get(sheet);
	}
	public String getSheetName(int sheet) {
		return sheets.get(sheet).getLabel();
	}
	public int findSheetByLangKey(String langKey) {
		for (int i=0;i<sheets.size();i++) {
			if (sheets.get(i).getLangKey().equals(langKey)) {
				return i;
			}
		}
		return -1;
	}
	
	
	public BaseExcel setColumns(Integer sheet, List<Column> columns) {
		if (isImportComment()) {
			columns.add(addColumn(-1, "icomment", 20000, STRING));
		}
		this.columns.put(sheet, columns);
		return this;
	}
	/**
	 * Excel columns
	 * @param sheet number
	 * @return
	 */
	public List<Column> getColumns (int sheet){
		return columns.get(sheet);
	}
	public Column addColumn(int index, String langKey, String label) {
		return new Column (index, langKey, label, null, null);
	}
	public Column addColumn(int index, String langKey, Integer width, Integer clazz) {
		return new Column (index, langKey, getLabel(langKey), width, clazz);
	}
	public int findColumnByLangKey(int sheet, String langKey) {
		List<Column> cols = getColumns (sheet);
		for (int i=0;i<cols.size();i++) {
			if (cols.get(i).getLangKey().equals(langKey)) {
				return i;
			}
		}
		return -1;
	}
	

	public String getLanguage() {
		return labels.getLanguage();
	}
	public String getLabel(String langKey) {
		return labels.getLabel(langKey);
	}
	public String getLabel(String langKey, boolean ignoreMissing) {
		return labels.getLabel(langKey, ignoreMissing);
	}

	
	public boolean isInvalid() {
		return isInvalid != null && isInvalid;
	}
	public void setIsInvalid(Boolean isInvalid) {
		this.isInvalid = isInvalid;
	}

	public Boolean isChanged() {
		return isChanged != null && isChanged;
	}
	public void setIsChanged(Boolean isChanged) {
		this.isChanged = isChanged;
	}

	public Boolean isImportComment() {
		return isImportComment != null && isImportComment;
	}
	public void setIsImportComment(Boolean isImportComment) {
		this.isImportComment = isImportComment;
	}

	
	
}
