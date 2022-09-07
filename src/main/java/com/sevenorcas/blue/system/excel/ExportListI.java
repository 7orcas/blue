package com.sevenorcas.blue.system.excel;

import java.util.List;

/**
* Standard interface for list type excel files to be exported
* 
* [Licence]
* Created 03.09.22
* @author John Stewart
*/
public interface ExportListI {

	public int getSheetCount();
	public String getSheetName(int sheet);
	public List<Column> getColumns (int sheet);
	public Integer getRowCount(int sheet);
	public Object getCell(int sheet, Integer row, Integer col);
	public void initialiseColumns();
	public boolean isRowInvalid(int sheet, Integer row);
}
