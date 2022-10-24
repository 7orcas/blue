package com.sevenorcas.blue.system.role;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import com.sevenorcas.blue.system.base.BaseServiceI;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntRole;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Roles Module service interface
*  
* Create 06.10.2022
* [Licence]
* @author John Stewart
*/

@Local
public interface SRoleI extends BaseServiceI {
	public JsonRes roleListJson(CallObject callObj, SqlParm parms) throws Exception;
	public List<EntRole> roleList(CallObject callObj, SqlParm parms) throws Exception;
    public JsonRes newRoleJson(CallObject callObj) throws Exception;
    public EntRole newRole(CallObject callObj) throws Exception;
    public JsonRes putRoles(CallObject callObj, List<EntRole> list) throws Exception;
	public Response excelExport(CallObject callObj) throws Exception;
}
