package com.sevenorcas.blue.system.log;

import org.junit.Test;

import com.sevenorcas.blue.BaseTest;

//THIS DOES NOTHING

public class AppLogTest extends BaseTest {

	//@Test
	public void testObjects() {
		try {
			int error = 1 / 0;
		} catch (Exception x) {
			AppLog.exception("Test", x);
		}
	}
}
