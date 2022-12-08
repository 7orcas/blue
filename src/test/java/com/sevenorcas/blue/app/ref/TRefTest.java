package com.sevenorcas.blue.app.ref;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.app.ref.ent.EntCountry;
import com.sevenorcas.blue.system.base.BaseEntityRef;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.ref.TRef;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.sql.SqlUpdate;

/**
 * Reference Module DAO bean test.
 * 
 * Created 08.12.2022
 * [Licence]
 * @author John Stewart
 */

public class TRefTest extends BaseTest {

	private int TEST_ORG_NR = 1;
	private CallObject callObj;
	private TRef refDao;
	
	@Before
	public void setup() throws Exception {
		refDao = setupEJBs(new TRef());
		callObj = getCallObject();
		callObj.getOrg().setOrgNr(TEST_ORG_NR);
	}
	
	@Test
	public void list () {
		try {
			SqlUpdate.executeQuery(null, "UPDATE "  
					+ tableName(EntCountry.class, " ") 
					+ "SET dvalue = true "
					+ "WHERE org_nr = " + callObj.getOrgNr()
					, null);
			
			refDao.resetDvalues(callObj, EntCountry.class);
			
			System.out.println("listJson - all");
			List<? extends BaseEntityRef<?>> countries = refDao.list(callObj, null, EntCountry.class);
			for (BaseEntityRef<?> ref : countries) {
				EntCountry ent = (EntCountry)ref;
				System.out.println(ent.getCode() + " " + ent.getImage() + " " + ent.isDvalue());	
			}
			
			System.out.println("listJson - active only");
			countries = refDao.list(callObj, new SqlParm().setActiveOnly(), EntCountry.class);
			for (BaseEntityRef<?> ref : countries) {
				EntCountry ent = (EntCountry)ref;
				System.out.println(ent.getCode() + " " + ent.getImage());	
			}
			
			assertTrue(true);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	}
	
	
	
}
