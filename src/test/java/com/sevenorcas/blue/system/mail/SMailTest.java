package com.sevenorcas.blue.system.mail;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;

public class SMailTest extends BaseTest {

	private SMail mailSrv;
	
	@Before
	public void setup() throws Exception {
		mailSrv = setupEJBs(new SMail());
	}
	
	@Test
	public void callDetails() {
		System.out.println(mailSrv.callDetails(callObject));
		assertTrue(true);
	}
	
	@Test
	public void stackTraceToString() {
		try {
			int x = 1/0;
			System.out.println(""+x);
		} catch (Exception e) {
			String t = mailSrv.stackTraceToString(e);
			System.out.println(t);
			int i = t.indexOf(SMail.class.getName());
			assertTrue(i>=0);
		}
	}
	
}
