package com.sevenorcas.blue.system.org;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;

/**
 * Organisation Module dao bean test.
 * 
 * Created 11.11.2022
 * [Licence]
 * @author John Stewart
 */

public class TOrgTest extends BaseTest {

	private TOrg orgDao;
	
	@Before
	public void setup() throws Exception {
		orgDao = setupEJBs(new TOrg());
		setTestData();
	}
	
	@Test
	public void findOrgId () {
		try {
			Long id = orgDao.findOrgId(ORG_NR);
			System.out.println("id=" + id);
			assertTrue(id.equals(Long.parseLong("" + ORG_NR)));
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	
	}
	
}
