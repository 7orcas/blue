package com.sevenorcas.blue.system.lang;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;

public class LangSrvTest extends BaseTest {

	private LangSrv langSrv;
	
	@Before
	public void setup() throws Exception {
		langSrv = new LangSrv();
		setupEJBs(langSrv);
	}
	
	@Test
	public void updateLabels () {
		try {
			//Read in file
			langSrv.updateLabels(
					1, (String)null, "en", (String)null,
					"/media/jarvisting/Jarvis/blue/import/1/LabelList.xlsx");
			
			assertTrue(0 == 0);
		} catch (Exception x) {
			showException(x);
			fail(x.getMessage());
		}
	
	}
	
}
