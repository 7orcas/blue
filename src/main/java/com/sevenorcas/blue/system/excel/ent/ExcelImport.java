package com.sevenorcas.blue.system.excel.ent;

import java.util.ArrayList;
import java.util.List;

import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;

/**
* Object to hold imported excel file 
* 
* [Licence]
* Created 27.8.22
* @author John Stewart
*/
public class ExcelImport extends BaseExcel {
	
	private List<List<List<Object>>> list;
	
	public ExcelImport (UtilLabel labels) {
		super (labels);
		list = new ArrayList<>();	
	}
	
	public ExcelImport addSheet(String label) {
		String langKey = "";
		if (labels.isLabel(label)) {
			langKey = labels.getLangKey(label);
		}
		sheets.add(new Sheet(sheets.size(), langKey, label));
		list.add(new ArrayList<>());
		return this;
	}
	
	public ExcelImport setColumns(Integer sheet, ArrayList<Object> objs) throws Exception {
		List<Column> cols = new ArrayList<>();
		for (int i=0;i<objs.size();i++) {
			Object o = objs.get(i);
			
			if (o == null) {
				//do nothing
			}
			else if (o instanceof String) {
				String label = o.toString();
				
				//Test if already exists
				for (Column c : cols) {
					if (c.getLabel().equals(label)) {
						throw new RedException("duplabel", "Duplicate column label in excel file: " + label);
					}
				}
				
				String langKey = "";
				if (labels.isLabel(label)) {
					langKey = labels.getLangKey(label);
				}
				cols.add(addColumn(i, langKey, label));
			}
		}
		setColumns(sheet, cols);
		return this;
	}
	
	public ExcelImport addRow (Integer sheet, ArrayList<Object> objs) {
		List<List<Object>> data = list.get(sheet);
		data.add(objs);
		return this;
	}
	
	public int getRowCount (Integer sheet) {
		List<List<Object>> data = list.get(sheet);
		return data.size();	
	}
	
	public List<Object> getRow (Integer sheet, Integer row) {
		List<List<Object>> data = list.get(sheet);
		return data.get(row);	
	}
	
	private Object getCellValue (Integer sheet, Integer row, Integer col) {
		List<List<Object>> data = list.get(sheet);
		List<Object> r = data.get(row);
		try{
			Object o = r.get(col);
			if (o.toString().equals(NULL)) {
				return null;
			}
			return o;
		} catch (Exception x) {
			return null;	
		}
	}
	
	public Long getCellLong (Integer sheet, Integer row, Integer col) {
		Object o = getCellValue(sheet, row, col);
		if (o == null) {
			return null;
		}
		return Double.valueOf(o.toString()).longValue();	
	}

	public Integer getCellInteger (Integer sheet, Integer row, Integer col) {
		Object o = getCellValue(sheet, row, col);
		if (o == null) {
			return null;
		}
		return Double.valueOf(o.toString()).intValue();	
	}

	public String getCellString (Integer sheet, Integer row, Integer col) {
		Object o = getCellValue(sheet, row, col);
		if (o == null) {
			return null;
		}
		return o.toString();	
	}
	
}
