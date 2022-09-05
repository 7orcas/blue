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
import com.sevenorcas.blue.system.lifecycle.CallObject;

public class ExcelSrvTest extends BaseTest {

	private ExcelSrv excelSrv;
	
	@Before
	public void setup() throws Exception {
		excelSrv = new ExcelSrv();
	}
	
	@Test
	public void readListFile() {
		try {
			//Read in file
			List<Map<Integer, List<Object>>> sheets = excelSrv.readListFile("/media/jarvisting/Jarvis/blue/import/1/LabelList.xlsx");
			
			for (int s=0;s<sheets.size(); s++) {
				Map<Integer, List<Object>> sheet = sheets.get(s);
				
				for (int r=0;r<sheet.size();r++) {
					List<Object> row = sheet.get(r);
					StringBuffer sb = new StringBuffer();
					for (int c=0;c<row.size();c++) {
						sb.append(row.get(c) + ",");
					}
					System.out.println(sb);
				}
			}
			assertTrue(0 == 0);
		} catch (Exception x) {
			showException(x);
			fail(x.getMessage());
		}
		

	}
	
}
