package com.sevenorcas.blue.system.role;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.role.ent.EntRole;
import com.sevenorcas.blue.system.role.ent.EntRolePermission;

/**
 * Role Module service bean test.
 * 
 * Created 28.09.2022
 * [Licence]
 * @author John Stewart
 */


public class SrvRoleTest extends BaseTest {

	private SRole service;
	
	@Before
	public void setup() throws Exception {
		service = new SRole();
		setupEJBs(service);
	}
	
	@Test
	public void list () {
		try {
			List<EntRole> list = service.roleList(getCallObject(), null);
			for (EntRole e : list) {
				System.out.println("id= " + e.getId() + " code=" + e.getCode());	
				for (EntRolePermission p : e.getPermissions()) {
					System.out.println("                 > id= " + p.getId() + " permission id=" + p.getPermissionId());	
				}
			}
			System.out.println("list size = " + list.size());
			assertTrue(list.size() == 4);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	
	}
	
}
