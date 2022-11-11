package com.sevenorcas.blue.system.org;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.org.ent.DtoOrg;
import com.sevenorcas.blue.system.org.ent.EntOrg;
import com.sevenorcas.blue.system.org.ent.ExcelOrg;
import com.sevenorcas.blue.system.org.ent.JsonOrg;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Organisation Module service bean.
*  
* Create 22 July 2022
* [Licence]
* @author John Stewart
*/

@Stateless
public class SOrg extends BaseService implements SOrgI {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	@EJB private TOrgI dao;
	
	@EJB private CacheOrg cache;
		
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
		JsonRes j = new JsonRes().setData(e.toJSon(callObj.getOrg()));
		return j;
    }
	
	/**
	 * Return an organisation entity via it's number
	 * This could be a cached entity
	 * 
	 * @param org nr
	 * @return
	 * @throws Exception
	 */
    public EntOrg getOrgCache(Integer nr) throws Exception {
    	
    	EntOrg org = cache.get(nr);
    	if (org != null) {
System.out.println("Org returned from cache");    		
			return org;
    	}
    	
    	Long id = dao.findOrgId(nr);
    	org = getOrg(id);
    	dao.detach(org);
    	org.decode();
    	cache.put(org.getOrgNr(), org);
System.out.println("Org added to cache");
    	return org;
    }
	
    /**
	 * Return an organisation entity
	 * 
	 * @param org id
	 * @return
	 * @throws Exception
	 */
    public EntOrg getOrg(Long orgId) throws Exception {
    	return dao.find(EntOrg.class, orgId);
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
    	y.add(o.toJSon(callObj.getOrg()));
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
		
		//Check validation errors
		ValidationErrors vals = validateSrv.validate(list, config);
		if (vals.hasErrors()) {
			UtilLabel u = langSrv.getLabelUtil(callObj.getOrgNr(), null, callObj.getLang(), null);
			vals.setLabels(u);
			return vals.toJSon();
		}
		
		//Set default value
		setDvalue(list);
		
		List<Long []> ids = new ArrayList<>();
  		try {
  			for (EntOrg ent : list) {
  				EntOrg mergedEnt = dao.put(ent, config, callObj);
  				
  				if (ent.getTempId() != null) {
  					Long id[] = {ent.getTempId(), ent.getId()};
  					ids.add(id);
  				}
  				//Merge non base fields
  				else if (!ent.isDelete()){
  					mergedEnt.encoder()
  							 .add("attempts", ent.getMaxLoginAttempts());
  					
  					mergedEnt.setDvalue(ent.isDvalue())
  							 .encode();
  				}
  			}
  			
  		} catch (Exception e) {
  			log.error(e);
  			throw e;
  		}
  		
  		return new JsonRes().setData(ids);
  	}  
   
    /**
     * Set a unique default value
     * Use first occurance
     * @param list
     */
    private void setDvalue(List<EntOrg> list) throws Exception {
    	boolean found = false;

    	for (int i=0;i<list.size();i++) {
    		EntOrg ent = list.get(i); 		
    		if (!ent.isDelete() && ent.isDvalue()) {
    			found = true;

    			for (int j=i+1;j<list.size();j++) {
    				ent = list.get(j);
    				ent.setDvalue(false);
    			}
    			break;
			}
    	}

    	if (found) {
    		dao.resetDvalues();
    	}
    	
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
