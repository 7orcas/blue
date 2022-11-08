package com.sevenorcas.blue.system.user;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.user.ent.EntUser;
import com.sevenorcas.blue.system.user.ent.EntUserRole;

/**
 * User Module service bean test.
 * 
 * Created 01.11.2022
 * [Licence]
 * @author John Stewart
 */
public class SUserTest extends BaseTest {

	private SUser userSrv;
	
	@Before
	public void setup() throws Exception {
		userSrv = setupEJBs(new SUser());
	}
	
	@Test
	public void list () {
		try {
			List<EntUser> list = userSrv.userList(callObject, null);
			for (EntUser e : list) {
				System.out.println(
						"id= " + e.getId() + " "
						+ "username=" + e.getUserName() + " "
						+ "password=" + e.getPassword() + " "
						+ "orgs=" + e.getOrgs() + " "
						+ "attempts=" + e.getAttempts() + " "
						);	
				
				
				for (EntUserRole p : e.getRoles()) {
					System.out.println("           role  > id= " + p.getId() + " user id=" + p.getUserId() + " role id=" + p.getRoleId());	
				}
				for (EntPermission p : e.getPermissions()) {
					System.out.println("           perm  > id= " + p.getId() + " crud=" + p.getCrud());	
				}
			}
			System.out.println("list size = " + list.size());
			assertTrue(true);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	
	}
	
}
