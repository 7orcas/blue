package com.sevenorcas.blue.system.excel;

import javax.ejb.Local;

import com.sevenorcas.blue.system.excel.ent.ExcelImport;
import com.sevenorcas.blue.system.excel.ent.ExportListI;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;

/**
* Excel file service interface 
* 
* [Licence]
* Created 27.8.22
* @author John Stewart
*/
@Local
public interface SExcel {
	public String createListFile(String filename, Integer orgNr, ExportListI list) throws Exception;
	public ExcelImport readListFile(String filename, UtilLabel labels) throws Exception;
}
