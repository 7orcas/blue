package com.sevenorcas.blue.system;

import org.junit.Test;

import com.sevenorcas.blue.BaseTest;

public class AppPropertiesTest extends BaseTest{

	@Test
	public void test() {
		AppProperties appProperties = AppProperties.getInstance();
		System.out.println(appProperties.get("WebClientMainUrl"));
	}
	
}
