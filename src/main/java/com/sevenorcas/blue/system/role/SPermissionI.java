package com.sevenorcas.blue.system.role;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import com.sevenorcas.blue.system.base.BaseServiceI;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Permissions Module service interface
*  
* Create 22 July 2022
* [Licence]
* @author John Stewart
*/

@Local
public interface SPermissionI extends BaseServiceI {
	public JsonRes permissonListJson(CallObject callObj, SqlParm parms) throws Exception;
    public JsonRes newPermissionJson(CallObject callObj) throws Exception;
    public EntPermission newPermission(CallObject callObj) throws Exception;
    public JsonRes putPermissions(CallObject callObj, List<EntPermission> list) throws Exception;
	public Response excelExport(CallObject callObj) throws Exception;
}
