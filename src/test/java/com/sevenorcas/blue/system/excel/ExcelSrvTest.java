package com.sevenorcas.blue.system.excel;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.excel.ent.ExcelImport;
import com.sevenorcas.blue.system.lang.SLang;
import com.sevenorcas.blue.system.lang.ent.DtoLabel;
import com.sevenorcas.blue.system.lang.ent.ExcelLabel;

public class ExcelSrvTest extends BaseTest {

	private SExcel excelSrv;
	private List<DtoLabel> list;
	
	@Before
	public void setup() throws Exception {
		excelSrv = setupEJBs(new SExcel());
		langSrv = setupEJBs(new SLang());
		list = langSrv.langPackage(1, null, "en", null);
	}
	
	@Test
	public void writeListFile() {
		try {
			//Read in file
			ExcelLabel excel = new ExcelLabel(util, list);
			String fn = excelSrv.createListFile("LabelListTest", 1, excel);
			System.out.println("File Name: " + fn);		
			assertTrue(fn.length()>0);
		} catch (Exception x) {
			showException(x);
			fail(x.getMessage());
		}
	}

	@Test
	public void writeListFileFailDuplicateSheet() {
		try {
			//Read in file
			ExcelLabel excel = new ExcelLabel(util, list);
			excel.addSheet(1, ExcelLabel.SHEET_LANGKEY);
			fail("Should not get here");
		} catch (Exception x) {
			assertTrue(true);
		}
	}
	
	
	@Test
	public void readListFile() {
		try {
			//Read in file
			ExcelImport excel = excelSrv.readListFile("/media/jarvisting/Jarvis/blue/import/1/LabelList.xlsx", util);
			
			for (int s=0;s<excel.getSheetCount(); s++) {
				System.out.println("Sheet: " + excel.getSheetName(s));
				
				for (int r=0;r<excel.getRowCount(s);r++) {
					List<Object> row = excel.getRow(s, r);
					StringBuffer sb = new StringBuffer();
					for (int c=0;c<row.size();c++) {
						sb.append(row.get(c) + ",");
					}
					System.out.println(sb);
				}
			}
					
			assertTrue(true);
		} catch (Exception x) {
			showException(x);
			fail(x.getMessage());
		}
		

	}
	
}
