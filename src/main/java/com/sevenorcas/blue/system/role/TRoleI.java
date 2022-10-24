package com.sevenorcas.blue.system.role;

import java.util.List;

import javax.ejb.Local;

import com.sevenorcas.blue.system.base.BaseTransferI;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntRole;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
 * Data access methods for role data interface
 * Created 05.10.22
 * [Licence]
 * @author John Stewart
 */

@Local
public interface TRoleI extends BaseTransferI {
	public List<EntRole> roleList(CallObject callObj, SqlParm parms) throws Exception;
}
