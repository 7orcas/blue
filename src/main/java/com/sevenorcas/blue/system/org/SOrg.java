package com.sevenorcas.blue.system.org;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.core.Response;

import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
 * Organisation Module service interface
 * Create 22 July 2022
 * [Licence]
 * @author John Stewart
 */
@Local
public interface SOrg {
	public JsonRes orgListJson(CallObject callObj, SqlParm parms) throws Exception;
	public JsonRes getOrgJson(CallObject callObj, Long orgId) throws Exception;
    public EntOrg getOrg(Long orgId) throws Exception;
    public JsonRes newOrgJson(CallObject callObj) throws Exception;
    public EntOrg newOrg(CallObject callObj) throws Exception;
    public JsonRes putOrgs(CallObject callObj, List<EntOrg> list) throws Exception;
	public Response excelExport(CallObject callObj) throws Exception;
}
