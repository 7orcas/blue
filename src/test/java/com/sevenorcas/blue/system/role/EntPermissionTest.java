package com.sevenorcas.blue.system.role;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.role.ent.EntPermission;

/**
 * Permission entity test.
 * 
 * Created 08.11.2022
 * [Licence]
 * @author John Stewart
 */
public class EntPermissionTest extends BaseTest {

	private EntPermission entPerm;
	
	@Before
	public void setup() throws Exception {
		entPerm = new EntPermission();
	}
	

	@Test
	public void combine () {
		String [] tests = {
				"*,crud,*", 
				"c,crud,*",
				"c,rud,*",
				"cr,crud,*",
				"cd,crd,CR-D",
				"dr,c,CR-D",
				",c,C---",
				"r,,-R--",
				
		};
		
		for (int i=0;i<tests.length;i++) {
			String [] test = tests[i].split(",");
			entPerm.setCrud(test[0]);
			entPerm.combine(test[1]);
			String result = entPerm.getCrud();
			System.out.println(test[0] + " + " + test[1] + " = " + result + " " + result.equals(test[2]));
			
			assertTrue(result.equals(test[2]));
		}
		
	}
	
	
}
