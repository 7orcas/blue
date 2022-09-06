package com.sevenorcas.blue.system.excel;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.exception.RedException;
import com.sevenorcas.blue.system.field.Encode;
import com.sevenorcas.blue.system.lang.LangSrv;
import com.sevenorcas.blue.system.lang.ent.LabelDto;
import com.sevenorcas.blue.system.lang.ent.LabelExcel;
import com.sevenorcas.blue.system.lang.ent.LabelUtil;
import com.sevenorcas.blue.system.lifecycle.CallObject;

public class ExcelSrvTest extends BaseTest {

	private ExcelSrv excelSrv;
	private LangSrv langSrv;
	private LabelUtil labels;
	private List<LabelDto> list;
	
	@Before
	public void setup() throws Exception {
		excelSrv = new ExcelSrv();
		setupEJBs(excelSrv);
		langSrv = new LangSrv();
		setupEJBs(langSrv);
		
		list = langSrv.langPackage(1, null, "en", null);
		labels = new LabelUtil(1, "en", listToHashtableCode (list));
	}
	
	@Test
	public void writeListFile() {
		try {
			//Read in file
			LabelExcel excel = new LabelExcel(labels, list);
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
			LabelExcel excel = new LabelExcel(labels, list);
			excel.addSheet(1, "Labels");
			fail("Should not get here");
		} catch (Exception x) {
			assertTrue(true);
		}
	}
	
	
	@Test
	public void readListFile() {
		try {
			//Read in file
			ExcelImport excel = excelSrv.readListFile("/media/jarvisting/Jarvis/blue/import/1/LabelList.xlsx", labels);
			
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
