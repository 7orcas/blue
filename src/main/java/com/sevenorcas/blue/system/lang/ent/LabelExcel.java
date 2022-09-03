package com.sevenorcas.blue.system.lang.ent;

import java.util.ArrayList;
import java.util.List;

import com.sevenorcas.blue.system.excel.ExcelListI;

/**
* Create a standard label excel file 
* 
* [Licence]
* Created 03.09.22
* @author John Stewart
*/
public class LabelExcel implements ExcelListI {

	private String sheetName;
	private LabelUtil labels;
	private List<LabelDto> table;
	
	public LabelExcel (
			String sheetName,
			LabelUtil labels,
			List<LabelDto> table) {
		this.sheetName = sheetName;
		this.labels = labels;
		this.table = table;
	}
	
	public String getSheetName() {
		return sheetName;
	}
	
	/**
	 * Excel column langKeys (<code>ExcelListI</code> required)
	 */
	public List<String> getColumns (){
		List<String> cols = new ArrayList<>();
		cols.add(labels.getLabel("id"));
		cols.add(labels.getLabel("lang"));
		cols.add(labels.getLabel("orgnr"));
		cols.add(labels.getLabel("langkey"));
		cols.add(labels.getLabel("label"));
		return cols;
	}

	public Integer getRowCount() {
		return table.size();
	}
	
	//ToDo
	public String getCell(Integer row, Integer col) {
		LabelDto l = table.get(row);
		switch (col) {
		
			case 0: return l.getId().toString();
			case 1: return labels.getLanguage();
			case 2: return l.getOrgNr().toString();
			case 3: return l.getCode();
			case 4: return l.getLabel();
			default: return "ERROR";
		}
	}
}
