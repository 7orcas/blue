package com.sevenorcas.blue.system.base;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;

/**
 * Base DAO bean test.
 * 
 * Created 07.10.2022
 * [Licence]
 * @author John Stewart
 */


public class BaseDaoTest extends BaseTest {

	private BaseDao dao;
	
	@Before
	public void setup() throws Exception {
		dao = new BaseDao();
	}
	
	@Test
	public void prefix () {
		try {
			String sql = dao.prefix("r", " f1,f2, f3 ");
			System.out.println("'" + sql + "'");
			assertTrue(sql.equals(" r.f1,r.f2,r.f3 "));
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	}
	
}
