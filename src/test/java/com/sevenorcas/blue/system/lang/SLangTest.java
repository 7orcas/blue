package com.sevenorcas.blue.system.lang;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.base.JsonRes;

public class SLangTest extends BaseTest {

	private SLang langSrv;
	
	@Before
	public void setup() throws Exception {
		langSrv = setupEJBs(new SLang());
	}
	
	@Test
	public void updateLabels () {
		try {
			//Read in file
			JsonRes r = langSrv.updateLabels(
					1, (String)null, "en", (String)null,
					"/home/jarvisting/Downloads/LabelList.xls");
			System.out.println(r.data);
			
			assertTrue(true);
		} catch (Exception x) {
			showException(x);
			fail(x.getMessage());
		}
	
	}
	
}
