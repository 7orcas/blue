package com.sevenorcas.blue.system.excel;

import java.util.List;

/**
* Standard interface for list type excel files to be imported
* 
* [Licence]
* Created 03.09.22
* @author John Stewart
*/
public interface ImportListI {

	public int getSheetCount();
	public String getSheetName(int sheet);
	public List<Column> getColumns (int sheet);
}
