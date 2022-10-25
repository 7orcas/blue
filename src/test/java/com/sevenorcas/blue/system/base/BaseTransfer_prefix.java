package com.sevenorcas.blue.system.base;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Base data transfer bean test - prefix.
 * Created 07.10.2022
 * [Licence]
 * @author John Stewart
 */
public class BaseTransfer_prefix extends BaseTransfer_ {

	public BaseTransfer_prefix() {
		super();
	}
	
	@Test
	public void prefix () {
		try {
			String sql = baseTransfer.prefix("r", " f1,f2, f3 ");
			System.out.println("'" + sql + "'");
			assertTrue(sql.equals(" r.f1,r.f2,r.f3 "));
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	}

	@Test
	public void prefixAS () {
		try {
			String sql = baseTransfer.prefixAs("r", " f1,f2, f3 ");
			System.out.println("'" + sql + "'");
			assertTrue(sql.equals(" r.f1 AS r_f1,r.f2 AS r_f2,r.f3 AS r_f3 "));
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	}

	
}
