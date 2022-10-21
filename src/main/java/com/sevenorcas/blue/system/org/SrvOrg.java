package com.sevenorcas.blue.system.org;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Validation;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.conf.SConfig;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.ent.DtoOrg;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.org.ent.ExcelOrg;
import com.sevenorcas.blue.system.org.json.JsonOrg;
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
public class SrvOrg extends BaseService {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	@EJB
	private DaoOrg dao;
	
	@EJB
	private SConfig configSrv;
	
	
	
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
		
		List<DtoOrg> x = dao.orgList(callObj, parms);
		List<JsonOrg> y = new ArrayList<JsonOrg>();
		for (DtoOrg d : x) {
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
			CallObject callObj,
			Long orgId) throws Exception {
		if (orgId == null) {
			return new JsonRes().setError("inv-id", "Invalid org id");
		}
		EntOrg e = getOrg(orgId);
		return new JsonRes().setData(e);
    }
	
    /**
	 * Return an organisation entity
	 * 
	 * @param org id
	 * @return
	 * @throws Exception
	 */
    public EntOrg getOrg(Long orgId) throws Exception {
    	return dao.getOrg(orgId);
    }
  
    
    /**
	 * Return an uncommitted organisation entity
	 * 
	 * @return
	 * @throws Exception
	 */
    public JsonRes newOrgJson(CallObject callObj) throws Exception {
    	EntOrg o = newOrg(callObj);
    	List<JsonOrg> y = new ArrayList<JsonOrg>();
    	y.add(o.toJSon());
		return new JsonRes().setData(y);
    }
  
    /**
	 * Return an uncommitted organisation entity
	 * 
	 * @return
	 * @throws Exception
	 */
    public EntOrg newOrg(CallObject callObj) throws Exception {
    	EntOrg o = new EntOrg();
    	o.setId(dao.nextTempIdNegative())
    	 .setOrgNr(0)
    	 .setActive()
    	 .setDvalue(false);
    	
    	return o;
    }  
    
    /**
	 * Create / Update / Delete the organisation list
	 * 
	 * @param callObj
	 * @param org entities to do CrUD on
	 * @throws Exception
	 */

    public JsonRes putOrgs(
    		CallObject callObj,
    		List<EntOrg> list) throws Exception {
		
		EntityConfig config = configSrv.getConfig(callObj, EntOrg.class);
		List<Validation> vals = new ArrayList<>();
	
//		//Validation
//		for (EntOrg ent : list) {
//			if (!ent.isValidEntity()) {
//				vals.add(ent.getValidation());
//			}
//		}
		
		//Errors
		if (vals.size() > 0) {
			return new JsonRes().setError("invlist").setData(vals);
		}
		
		List<Long []> ids = new ArrayList<>();
  		try {
  			for (EntOrg ent : list) {
  				dao.put(ent, config, callObj);
  				
  				if (ent.getTempId() != null) {
  					Long id[] = {ent.getTempId(), ent.getId()};
  					ids.add(id);
  				}
  			}
  			
  		} catch (Exception e) {
  			log.error(e);
  			throw e;
  		}
  		
  		return new JsonRes().setData(ids);
  	}  
   
    /**
	 * Export organisations to excel
	 * @return
	 * @throws Exception
	 */
	public Response excelExport(CallObject callObj) throws Exception {
		
		List<DtoOrg> x = dao.orgList(callObj, null);
		UtilLabel labels = langSrv.getLabelUtil(callObj.getOrgNr(), null, callObj.getLang(), null);
		ExcelOrg excel = new ExcelOrg(labels, x);
		
		String fn = excelSrv.createListFile("OrgList", callObj.getOrgNr(), excel);
		return fileSrv.getFile(fn, "OrgList.xlsx", false);
    }
    
}
