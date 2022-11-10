package com.sevenorcas.blue.system.user;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import com.sevenorcas.blue.system.base.BaseServiceI;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.sql.SqlParm;
import com.sevenorcas.blue.system.user.ent.EntUser;

/**
* User Module service interface
*  
* Create 01.11.2022
* [Licence]
* @author John Stewart
*/
@Local
public interface SUserI extends BaseServiceI {
	public JsonRes userListJson(CallObject callObj, SqlParm parms) throws Exception;
	public List<EntUser> userList(CallObject callObj, SqlParm parms) throws Exception;
	public JsonRes getUserJson(CallObject callObj, Long id) throws Exception;
    public EntUser getUser(CallObject callObj, Long id) throws Exception;
    public JsonRes newUserJson(CallObject callObj) throws Exception;
    public EntUser newUser(CallObject callObj) throws Exception;
    public JsonRes putUsers(CallObject callObj, List<EntUser> list) throws Exception;
    public void putConfig(CallObject callObj,String config, String value)  throws Exception;
	public Response excelExport(CallObject callObj) throws Exception;
}
