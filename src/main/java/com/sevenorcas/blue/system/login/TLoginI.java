package com.sevenorcas.blue.system.login;

import java.util.List;

import javax.ejb.Local;

import com.sevenorcas.blue.system.base.BaseTransferI;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.user.EntUser;

/**
* Data access methods to the Login Module interface
* 
* Created July '22
* [Licence]
* @author John Stewart
*/

@Local
public interface TLoginI extends BaseTransferI {
	public EntUser getUser (String userid);
	public String getUserid (Long userId, SqlParm parms) throws Exception;
	public List<String> getUserRoles (Long userId, SqlParm parms) throws Exception;	
}
