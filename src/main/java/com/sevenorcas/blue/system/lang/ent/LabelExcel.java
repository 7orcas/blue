package com.sevenorcas.blue.system.lang.ent;

import java.util.ArrayList;
import java.util.List;

import com.sevenorcas.blue.system.excel.BaseExcel;
import com.sevenorcas.blue.system.excel.Column;
import com.sevenorcas.blue.system.excel.ExcelImport;
import com.sevenorcas.blue.system.excel.ExportListI;
import com.sevenorcas.blue.system.excel.ImportListI;
import com.sevenorcas.blue.system.exception.RedException;

/**
* Create a standard label excel file 
* Used in both export and import
* 
* [Licence]
* Created 03.09.22
* @author John Stewart
*/
public class LabelExcel extends BaseExcel implements ExportListI, ImportListI {

	final private static String SHEET_NAME = "Labels";
	private List<LabelDto> table;
	private int sheet;
	

	/**
	 * Export Constructor
	 * @param labels
	 * @param table
	 * @throws Exception
	 */
	public LabelExcel (LabelUtil labels,
			List<LabelDto> table) throws Exception {
		super (labels);
		addSheet(0, SHEET_NAME);
		this.table = table;

		//Add columns
		initialiseColumns();
	}

	/**
	 * Import Constructor
	 * @param labels
	 * @param import excel
	 * @throws Exception
	 */
	public LabelExcel (LabelUtil labels,
			ExcelImport excel) throws Exception {
		super (labels, excel);		
		sheet = findSheetByLangKey(SHEET_NAME);
		if (sheet == -1) {
			throw new RedException("invexcel", "Can't find sheet name: " + getLabel(SHEET_NAME, true));
		}
	
		//Get Column indexes
		initialiseColumns();
		List<Column> cols = getColumns (sheet);
		for (int i=0;i<cols.size();i++) {
			int index = findColumnByLangKey(sheet, cols.get(i).getLangKey());
			if (index == -1) {
				throw new RedException("invexcel", "Can't find column name: " + getLabel(cols.get(i).getLangKey(), true));
			}
			cols.get(i).setIndex(index);
		}
		
	}
	
	private void initialiseColumns() {
		List<Column> cols = new ArrayList<>();
		cols.add(addColumn(-1, "id", 1500, LONG));
		cols.add(addColumn(-1, "lang", 2000, STRING));
		cols.add(addColumn(-1, "orgnr-s", 2000, INTEGER));
		cols.add(addColumn(-1, "langkey", 6000, STRING));
		cols.add(addColumn(-1, "label", 20000, STRING));
		setColumns(0, cols);
	}
	
	
	/**
	 * Populate the label DTO's
	 */
	public void populate (List<LabelDto> table) {
		this.table = table;
		
		for (int r=0;r<excel.getRowCount(sheet);r++) {
			List<Object> row = excel.getRow(sheet, r);
			
			for (int c=0;c<row.size();c++) {
				sb.append(row.get(c) + ",");
			}
			
		}

		
	}
	
	
	public Integer getRowCount(int sheet) {
		return table.size();
	}
	
	/**
	 * Excel values
	 * @param index sheet number
	 * @param row
	 * @param column
	 */
	public Object getCell(int sheet, Integer row, Integer col) {
		LabelDto l = table.get(row);
		switch (col) {
			case 0: return l.getId();
			case 1: return getLanguage();
			case 2: return l.getOrgNr();
			case 3: return l.getCode();
			case 4: return l.getLabel();
			default: return "ERROR";
		}
	}
}
