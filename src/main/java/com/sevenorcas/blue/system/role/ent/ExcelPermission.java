package com.sevenorcas.blue.system.role.ent;

import java.util.ArrayList;
import java.util.List;

import com.sevenorcas.blue.system.excel.BaseExcel;
import com.sevenorcas.blue.system.excel.Column;
import com.sevenorcas.blue.system.excel.ExportListI;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;

/**
* Create a standard permissions excel file 
* 
* [Licence]
* Created 05.10.22
* @author John Stewart
*/
public class ExcelPermission extends BaseExcel implements ExportListI {

	final private static String SHEET_LANGKEY = "Permissions";
	private List<EntPermission> table;
	
	/**
	 * Export Constructor
	 * @param labels
	 * @param table
	 * @throws Exception
	 */
	public ExcelPermission (UtilLabel labels,
			List<EntPermission> table) throws Exception {
		super (labels);
		addSheet(0, SHEET_LANGKEY);
		this.table = table;
	}

	
	public void initialiseColumns() {
		List<Column> cols = new ArrayList<>();
		cols.add(addColumn(-1, "id", 1500, LONG));
		cols.add(addColumn(-1, "orgnr-s", 2000, INTEGER));
		cols.add(addColumn(-1, "code", 6000, STRING));
		cols.add(addColumn(-1, "desc", 20000, STRING));
		cols.add(addColumn(-1, "crud", 2000, STRING));
		setColumns(0, cols);
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
		EntPermission l = table.get(row);
		switch (col) {
			case 0: return l.getId();
			case 1: return l.getOrgNr();
			case 2: return l.getCode();
			case 3: return l.getDescr();
			case 4: return l.getCrud();
			default: return "ERROR";
		}
	}
	
	public boolean isRowInvalid(int sheet, Integer row) {
		EntPermission l = table.get(row);
		return !l.isValid();
	}
	
}
