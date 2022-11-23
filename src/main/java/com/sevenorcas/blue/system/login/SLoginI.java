package com.sevenorcas.blue.system.login;

import java.util.List;

import javax.ejb.Local;

import com.sevenorcas.blue.system.base.BaseServiceI;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.user.ent.EntUser;

/**
* Login interface
* 
* Created July '22
* [Licence]
* @author John Stewart
*/

@Local
public interface SLoginI extends BaseServiceI {
	public EntUser getUserAndValidate (String userid, String pw, String adminPw, Integer org, String lang) throws Exception;
	public String getUserid (Long userId) throws Exception;
	public List<String> getUserRoles (Long userId) throws Exception;
	public String getUserRolesAsString (Long userId)  throws Exception;
	public List<EntPermission> permissionList(Long userId) throws Exception;
	public void detach (EntUser ent) throws Exception;
	public EntUser persistAfterLogin (EntUser ent) throws Exception;
	public JsonRes emailTempPassword (String email, String lang) throws Exception;
	public JsonRes logout(CallObject callOb, Long userId) throws Exception;
	public JsonRes listCacheJson(CallObject callObj) throws Exception;
}
