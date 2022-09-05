package com.sevenorcas.blue.system.lang.ent;

import java.util.ArrayList;
import java.util.List;

import com.sevenorcas.blue.system.excel.BaseExcel;
import com.sevenorcas.blue.system.excel.Column;
import com.sevenorcas.blue.system.excel.ExportListI;

/**
* Create a standard label excel file 
* Used in both export and import
* 
* [Licence]
* Created 03.09.22
* @author John Stewart
*/
public class LabelExcel extends BaseExcel implements ExportListI {

	private List<LabelDto> table;
	
	public LabelExcel (LabelUtil labels,
			List<LabelDto> table) {
		super (labels);
		addSheetName("Labels");
		this.table = table;
	}
	
	/**
	 * Excel column langKeys (<code>ExcelListI</code> required)
	 * @param index sheet number
	 * @return
	 */
	public List<Column> getColumns (int index){
		List<Column> cols = new ArrayList<>();
		cols.add(addColumn("id", 1500, LONG));
		cols.add(addColumn("lang", 2000, STRING));
		cols.add(addColumn("orgnr-s", 2000, INTEGER));
		cols.add(addColumn("langkey", 6000, STRING));
		cols.add(addColumn("label", 20000, STRING));
		return cols;
	}

	public Integer getRowCount(int index) {
		return table.size();
	}
	
	/**
	 * Excel values
	 * @param index sheet number
	 * @param row
	 * @param column
	 */
	public Object getCell(int index, Integer row, Integer col) {
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
