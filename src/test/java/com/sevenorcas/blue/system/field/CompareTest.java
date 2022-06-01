package com.sevenorcas.blue.system.field;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import com.sevenorcas.blue.BaseTest;

public class CompareTest extends BaseTest{

	@Test
	public void test() {
		assertTrue(Compare.isSame(1, 1) == true);
		assertTrue(Compare.isSame(1, 2) == false);
		assertTrue(Compare.isSame(1.5, 1.5) == true);
		assertTrue(Compare.isSame(1.5, 2.5) == false);
		assertTrue(Compare.isSame(1L, 1L) == true);
		assertTrue(Compare.isSame(1L, 2L) == false);
		assertTrue(Compare.isSame("x", "x") == true);
		assertTrue(Compare.isSame("x", "X") == false);
		assertTrue(Compare.isSame(LocalDate.parse("1962-03-14"), LocalDate.parse("1962-03-14")) == true);
		assertTrue(Compare.isSame(LocalTime.now(), LocalTime.now()) == false);
		assertTrue(Compare.isSame(null, null) == true);
		assertTrue(Compare.isSame(1, null) == false);
	}
	
	
}
