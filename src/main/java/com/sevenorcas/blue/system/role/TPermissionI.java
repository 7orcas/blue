package com.sevenorcas.blue.system.role;

import java.util.List;

import javax.ejb.Local;

import com.sevenorcas.blue.system.base.BaseTransferI;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Data access methods for permission data interface
* Created 05.10.22
* [Licence]
* @author John Stewart
*/

@Local
public interface TPermissionI extends BaseTransferI {
	public List<EntPermission> permissionList(CallObject callObj, SqlParm parms) throws Exception;
    public EntPermission getPermission (Long id) throws Exception;
}
