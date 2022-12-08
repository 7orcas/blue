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
import com.sevenorcas.blue.system.base.BaseEntityRef;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.ref.SRef;
import com.sevenorcas.blue.system.ref.TRef;

/**
 * Reference Module service bean test.
 * 
 * Created 05.12.2022
 * [Licence]
 * @author John Stewart
 */

public class SRefTest extends BaseTest {

	private int TEST_ORG_NR = 1;
	private CallObject callObj;
	private SRef refServ;
	private TRef refDao;
	
	@Before
	public void setup() throws Exception {
		refServ = setupEJBs(new SRef());
		refDao = setupEJBs(new TRef());
		callObj = getCallObject();
		callObj.getOrg().setOrgNr(TEST_ORG_NR);
	}
	
	
	@SuppressWarnings("unused")
	private EntCountry findEntCountry (String code) throws Exception {
		List<? extends BaseEntityRef<?>> countries = refDao.list(callObj, null, EntCountry.class);
		for (BaseEntityRef<?> ref : countries) {
			EntCountry ent = (EntCountry)ref;
			if (ent.getCode().equals(code)) return ent;	
		}
		return null;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void newJson () {
		System.out.println("newJson");
		try {
			JsonRes json = refServ.newJson(callObj, EntCountry.class);
			List<JsonCountry> countries = (List)json.data;
			System.out.println(countries.get(0).code);	
			
			json = refServ.newJson(callObj, EntCurrency.class);
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
		System.out.println("listJson");
		try {
			JsonRes json = refServ.listJson(callObj, null, EntCountry.class);
			List<JsonCountry> countries = (List)json.data;
			for (JsonCountry j : countries) {
				System.out.println(j.code);	
			}
			
			json = refServ.listJson(callObj, null, EntCurrency.class);
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
