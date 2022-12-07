package com.sevenorcas.blue.app.ref;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.app.ref.ent.EntCountry;
import com.sevenorcas.blue.app.ref.ent.EntCurrency;
import com.sevenorcas.blue.app.ref.ent.JsonCountry;
import com.sevenorcas.blue.app.ref.ent.JsonCurrency;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.ref.SRef;

/**
 * Reference Module service bean test.
 * 
 * Created 05.12.2022
 * [Licence]
 * @author John Stewart
 */

public class SRefTest extends BaseTest {

	private SRef refServ;
	
	@Before
	public void setup() throws Exception {
		refServ = setupEJBs(new SRef());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void newJson () {
		try {
			JsonRes json = refServ.newJson(getCallObject(), EntCountry.class);
			List<JsonCountry> countries = (List)json.data;
			System.out.println(countries.get(0).code);	
			
			json = refServ.newJson(getCallObject(), EntCurrency.class);
			List<JsonCurrency> currencies = (List)json.data;
			System.out.println(currencies.get(0).code);	
			
			assertTrue(true);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void listJson () {
		try {
			JsonRes json = refServ.listJson(getCallObject(), null, EntCountry.class);
			List<JsonCountry> countries = (List)json.data;
			for (JsonCountry j : countries) {
				System.out.println(j.code);	
			}
			
			json = refServ.listJson(getCallObject(), null, EntCurrency.class);
			List<JsonCurrency> currencies = (List)json.data;
			for (JsonCurrency j : currencies) {
				System.out.println(j.code);	
			}
			
			assertTrue(true);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	}
	
}
