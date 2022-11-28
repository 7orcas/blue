package com.sevenorcas.blue.system.user;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.user.ent.EntUser;

/**
 * User Entity test.
 * 
 * Created 28.11.2022
 * [Licence]
 * @author John Stewart
 */
public class EntUserTest extends BaseTest {

	private EntUser userEnt;
	private String [][] perms = {
			{"a",     "CRUD"},
			{"a/b",   "*"},
			{"a/c",   "-R--"},
			{"a/b/d", "C---"},
			{"a/d",   "*"}
	};
	
	//Test Url and expected result
	private String [][] urls = {
			{"b",     ""},
			{"a",     "a"},
			{"a/",    "a"},
			{"a/e",   "a"},
			{"a/c",   "a/c"},
			{"a/c/e", "a/c"},
			{"b/d",   ""},
			{"a/d",   "a/d"}
	};
	
	@Before
	public void setup() throws Exception {
		userEnt = new EntUser();

		List<EntPermission> permissions = new ArrayList<>();
		for (String[]url : perms) {
			EntPermission p = new EntPermission()
					.setCode(url[0])
					.setCrud(url[1]);
			permissions.add(p);
		}
		userEnt.setPermissions(permissions);
	}
	
	@Test
	public void permissionUrl () {
		try {
			for (String[]url : urls) {
				EntPermission p = userEnt.findPermission(url[0]);
				System.out.println(url[0] + "=" + (p!=null?p.getCode():"null"));
				
				if (p == null && url[1].length() == 0)
					assertTrue(true);
				else if (p.getCode().equals(url[1]))
					assertTrue(true);
				else
					assertTrue(false);
			}
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	
	}
	
}
