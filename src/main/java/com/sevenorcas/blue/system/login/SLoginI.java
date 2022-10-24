package com.sevenorcas.blue.system.login;

import java.util.List;

import javax.ejb.Local;

import com.sevenorcas.blue.system.base.BaseServiceI;
import com.sevenorcas.blue.system.user.EntUser;

/**
* Login interface
* 
* Created July '22
* [Licence]
* @author John Stewart
*/

@Local
public interface SLoginI extends BaseServiceI {
	public EntUser getUserAndValidate (String userid, String pw, Integer org);
	public String getUserid (Long userId) throws Exception;
	public List<String> getUserRoles (Long userId) throws Exception;
	public String getUserRolesAsString (Long userId)  throws Exception;
}
