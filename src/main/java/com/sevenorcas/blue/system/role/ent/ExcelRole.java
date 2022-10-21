package com.sevenorcas.blue.system.role.ent;

import java.util.ArrayList;
import java.util.List;

import com.sevenorcas.blue.system.excel.ent.BaseExcel;
import com.sevenorcas.blue.system.excel.ent.Column;
import com.sevenorcas.blue.system.excel.ent.ExportListI;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;

/**
* Create a standard roles excel file 
* 
* [Licence]
* Created 05.10.22
* @author John Stewart
*/
public class ExcelRole extends BaseExcel implements ExportListI {

	final private static String SHEET_LANGKEY = "Roles";
	private List<EntRole> table;
	
	/**
	 * Export Constructor
	 * @param labels
	 * @param table
	 * @throws Exception
	 */
	public ExcelRole (UtilLabel labels,
			List<EntRole> table) throws Exception {
		super (labels);
		addSheet(0, SHEET_LANGKEY);
		this.table = table;
	}

	
	public void initialiseColumns() {
		List<Column> cols = new ArrayList<>();
		cols.add(addColumn(-1, "id", 1500, LONG));
		cols.add(addColumn(-1, "orgnr-s", 2000, INTEGER));
		cols.add(addColumn(-1, "role", 3000, STRING));
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
		EntRole l = table.get(row);
		switch (col) {
			case 0: return l.getId();
			case 1: return l.getOrgNr();
			case 2: return l.getCode();
			default: return "ERROR";
		}
	}
	
	public boolean isRowInvalid(int sheet, Integer row) {
		EntRole l = table.get(row);
		return !l.isValid();
	}
	
}
