package com.sevenorcas.blue.system.role;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseService;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.conf.SConfigI;
import com.sevenorcas.blue.system.conf.ent.EntityConfig;
import com.sevenorcas.blue.system.conf.ent.ValidationErrors;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntPermission;
import com.sevenorcas.blue.system.role.ent.ExcelPermission;
import com.sevenorcas.blue.system.role.ent.JsonPermission;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Permissions Module service bean.
*  
* Create 22 July 2022
* [Licence]
* @author John Stewart
*/

@Stateless
public class SPermission extends BaseService implements SPermissionI {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	@EJB
	private TPermissionI dao;
	
	@EJB
	protected SConfigI configSrv;
		
	/**
	 * List of permission objects
	 * These are not the full entity
	 *  
	 * @param callObj
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public JsonRes permissonListJson(
			CallObject callObj,
    		SqlParm parms) throws Exception{
		
		List<EntPermission> x = dao.permissionList(callObj, parms);
		List<JsonPermission> y = new ArrayList<>();
		for (EntPermission d : x) {
			y.add(d.toJSon());
		}
		
		return new JsonRes().setData(y);
    }
	
	
    /**
	 * Return an uncommitted permission entity
	 * 
	 * @return
	 * @throws Exception
	 */
    public JsonRes newPermissionJson(CallObject callObj) throws Exception {
    	EntPermission o = newPermission(callObj);
    	List<JsonPermission> y = new ArrayList<>();
    	y.add(o.toJSon());
		return new JsonRes().setData(y);
    }
  
    /**
	 * Return an uncommitted permission entity
	 * 
	 * @return
	 * @throws Exception
	 */
    public EntPermission newPermission(CallObject callObj) throws Exception {
    	EntPermission o = new EntPermission();
    	return o.setId(dao.nextTempIdNegative())
    			.setOrgNr(BASE_ORG_NR)
		    	.setActive()
		    	.setCrud("*");
    }  
    
    /**
	 * Create / Update / Delete the permission list
	 * 
	 * @param callObj
	 * @param entities to do CrUD on
	 * @throws Exception
	 */

    public JsonRes putPermissions(
    		CallObject callObj,
    		List<EntPermission> list) throws Exception {
		
		EntityConfig config = configSrv.getConfig(callObj, EntPermission.class);
		
		//Check validation errors
		ValidationErrors vals = validateSrv.validate(list, config);
		if (vals.hasErrors()) {
			UtilLabel u = langSrv.getLabelUtil(callObj.getOrgNr(), null, callObj.getLang(), null);
			vals.setLabels(u);
			return vals.toJSon();
		}
		
		
		List<Long []> ids = new ArrayList<>();
  		try {
  			for (EntPermission ent : list) {
  				ent.formatCrud();
  				
  				EntPermission mergedEnt = dao.put(ent, config, callObj);
  				
  				if (ent.getTempId() != null) {
  					Long id[] = {ent.getTempId(), ent.getId()};
  					ids.add(id);
  				}
  				//Merge non base fields
  				else if (!ent.isDelete()){
  					mergedEnt.setCrud(ent.getCrud());
  				}
  			}
  			
  		} catch (Exception e) {
  			log.error(e);
  			throw e;
  		}
  		
  		return new JsonRes().setData(ids);
  	}  
   
    /**
	 * Export permissions to excel
	 * @return
	 * @throws Exception
	 */
	public Response excelExport(CallObject callObj) throws Exception {
		
		List<EntPermission> x = dao.permissionList(callObj, null);
		UtilLabel labels = langSrv.getLabelUtil(callObj.getOrgNr(), null, callObj.getLang(), null);
		ExcelPermission excel = new ExcelPermission(labels, x);
		
		String fn = excelSrv.createListFile("PermissionList", callObj.getOrgNr(), excel);
		return fileSrv.getFile(fn, "PermissionList.xlsx", false);
    }
    
}
