package com.sevenorcas.blue.system.org;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.ent.OrgDto;
import com.sevenorcas.blue.system.org.ent.OrgEnt;
import com.sevenorcas.blue.system.org.json.OrgJsonRes;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Create 22 July 2022
* 
* Organisation Module service bean.
* * 
* [Licence]
* @author John Stewart
*/

@Stateless
public class OrgSrv extends BaseSrv {

	@EJB
	private OrgDao dao;
	
	public JsonRes orgListJson(
    		CallObject callObj,
    		SqlParm parms) throws Exception{
		
		List<OrgDto> x = dao.orgList(callObj, parms);
		List<OrgJsonRes> y = new ArrayList<OrgJsonRes>();
		for (OrgDto d : x) {
			y.add(d.toJSon());
		}
		
		return new JsonRes().setData(y);
    }
	
	/**
	 * Return an organisation entity
	 * 
	 * @param callObj
	 * @param org id
	 * @return
	 * @throws Exception
	 */
   public JsonRes getOrgJson(
   		@QueryParam ("co") CallObject callObj,
   		@QueryParam ("id") Long orgId) throws Exception {
		if (orgId == null) {
			return new JsonRes().setError("inv-id", "Invalid org id");
		}
		OrgEnt e = getOrg(orgId);
		OrgJsonRes j = e.toJSon();
		
		return new JsonRes().setData(j);
   }
	
   /**
	 * Return an organisation entity
	 * 
	 * @param org id
	 * @return
	 * @throws Exception
	 */
  public OrgEnt getOrg(Long orgId) throws Exception {
		return dao.getOrg(orgId);
  }
   
}
