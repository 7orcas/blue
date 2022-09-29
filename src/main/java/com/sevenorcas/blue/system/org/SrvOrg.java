package com.sevenorcas.blue.system.org;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.field.validationDEL.Validation;
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
public class SrvOrg extends BaseSrv {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	@EJB
	private DaoOrg dao;
	
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
		
		List<Validation> vals = new ArrayList<>();
		List<Long []> ids = new ArrayList<>();
	
		//Validation
		for (EntOrg ent : list) {
			if (!ent.isValidEntity()) {
				vals.add(ent.getValidation());
			}
		}
		
		//Errors
		if (vals.size() > 0) {
			return new JsonRes().setError("invlist").setData(vals);
		}
		
  		try {
  			for (EntOrg ent : list) {
  				if (ent.isDelete()) {
  					dao.deleteOrg(ent.getId());
  				}
  				else if (ent.isValidId()) {
  					dao.mergeOrg (ent);
  				}
  				else if (ent.isNew()){
  					Long[] id = new Long[2];
  					ids.add(id);
  					id[0] = ent.getId();
  					ent.setId(null);
  					id[1] = dao.persistOrg(ent);
  				}
  			}
  			
  		} catch (Exception e) {
  			log.equals(e);
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
