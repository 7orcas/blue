package com.sevenorcas.blue.system.excel;

import java.util.List;

/**
* Standard interface for list type excel files 
* 
* [Licence]
* Created 03.09.22
* @author John Stewart
*/
public interface ExcelListI {

	public String getSheetName();
	public List<String> getColumns ();
	public Integer getRowCount();
	public String getCell(Integer row, Integer col);
}
