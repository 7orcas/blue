package com.sevenorcas.blue.system.user;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.sevenorcas.blue.BaseTest;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.user.ent.EntUser;

/**
 * User Module DAO bean test.
 * 
 * Created 08.11.2022
 * [Licence]
 * @author John Stewart
 */
public class TUserTest extends BaseTest {

	private TUser userDao;
	private EntUser user;
	
	@Before
	public void setup() throws Exception {
		userDao = setupEJBs(new TUser());
		user = new EntUser();
		user.setId(1L);
	}
	
	@Test
	public void permissions () {
		try {
			List<EntPermission> list = userDao.permissionList(null, user.getId());
			
			for (EntPermission e : list) {
				System.out.println(
						"url= " + e.getCode() + " "
						+ "crud=" + e.getCrud() + " "
						);	
			}
			assertTrue(true);
			
		} catch (Exception e) {
			System.out.println("EX:" + e);
			fail(e.getMessage());
		}
	
	}
	
}
