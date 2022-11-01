package com.sevenorcas.blue.system.user;

import java.util.List;

import javax.ejb.Local;

import com.sevenorcas.blue.system.base.BaseTransferI;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.user.ent.EntUser;

/**
 * Data access methods for user data interface
 * Created 01.11.22
 * [Licence]
 * @author John Stewart
 */

@Local
public interface TUserI extends BaseTransferI {
	public List<EntUser> userList(CallObject callObj, SqlParm parms) throws Exception;
}
