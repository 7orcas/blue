package com.sevenorcas.blue.app.ref;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.app.ref.ent.EntCountry;
import com.sevenorcas.blue.app.ref.ent.JsonCountry;
import com.sevenorcas.blue.system.base.BaseEntityRef;
import com.sevenorcas.blue.system.base.JsonRes;

/**
 * Reference Module service bean test.
 * 
 * Created 28.09.2022
 * [Licence]
 * @author John Stewart
 */

public class SRefTest extends BaseTest {

	private SRef refServ;
	
	@Before
	public void setup() throws Exception {
		refServ = setupEJBs(new SRef());
	}
	
	@Test
	public void listJson () {
		try {
			JsonRes json = refServ.listJson(getCallObject(), null, EntCountry.class);
			List<JsonCountry> list = (List)json.data;
			for (JsonCountry j : list) {
				System.out.println(j.code + " " + j.x);	
			}
			assertTrue(true);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	
	}
	
}
