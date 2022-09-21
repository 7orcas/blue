package com.sevenorcas.blue.system.org;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.ent.OrgDto;
import com.sevenorcas.blue.system.org.ent.OrgEnt;
import com.sevenorcas.blue.system.org.json.OrgJson;
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
	
	/**
	 * List of organisation objects
	 * These are not the full entity
	 *  
	 * @param callObj
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public JsonRes orgListJson(
			CallObject callObj,
    		SqlParm parms) throws Exception{
		
		List<OrgDto> x = dao.orgList(callObj, parms);
		List<OrgJson> y = new ArrayList<OrgJson>();
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
		return new JsonRes().setData(e);
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
  
  
    /**
	 * Create / Update / Delete the organisation list
	 * 
	 * @param callObj
	 * @param org entities to do CrUD on
	 * @throws Exception
	 */
@Transactional  //working?
    public void putOrgs(
    		CallObject callObj,
    		List<OrgEnt> list) throws Exception {
		
		//ToDo validation
	
  		try {
  		
  			for (OrgEnt ent : list) {
  				if (ent.isDelete()) {
  					dao.deleteOrg(ent.getId());
  				}
  				else if (ent.isValidId()) {
  					dao.mergeOrg (ent);
  				}
  				else if (ent.isNew()){
  					ent.setId(null);
  					dao.persistOrg(ent);
  				}
  			}
//ToDo not working  			
dao.getEntityManager().getTransaction().commit();
  			
  		} catch (Exception e) {
  		}
  	}  
   
}
