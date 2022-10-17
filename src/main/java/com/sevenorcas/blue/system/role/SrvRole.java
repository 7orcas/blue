package com.sevenorcas.blue.system.role;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.sevenorcas.blue.system.base.BaseSrv;
import com.sevenorcas.blue.system.base.JsonRes;
import com.sevenorcas.blue.system.conf.EntityConfig;
import com.sevenorcas.blue.system.conf.ValidationErrors;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntRole;
import com.sevenorcas.blue.system.role.ent.EntRolePermission;
import com.sevenorcas.blue.system.role.ent.ExcelRole;
import com.sevenorcas.blue.system.role.json.JsonRole;
import com.sevenorcas.blue.system.sql.SqlParm;

/**
* Roles Module service bean.
*  
* Create 06.10.2022
* [Licence]
* @author John Stewart
*/

@Stateless
public class SrvRole extends BaseSrv {

	private static Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
	
	@EJB
	private DaoRole dao;
	
	/**
	 * List of role objects
	 * These are not the full entity
	 *  
	 * @param callObj
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public JsonRes roleListJson(
			CallObject callObj,
    		SqlParm parms) throws Exception{
		
		List<EntRole> x = roleList(callObj, parms);
		List<JsonRole> y = new ArrayList<>();
		for (EntRole d : x) {
			y.add(d.toJSon());
		}
		
		return new JsonRes().setData(y);
    }
	
	/**
	 * List of role objects
	 * These are not the full entity
	 *  
	 * @param callObj
	 * @param parms
	 * @return
	 * @throws Exception
	 */
	public List<EntRole> roleList(
			CallObject callObj,
    		SqlParm parms) throws Exception{
		return dao.roleList(callObj, parms);
    }
	
	
	
    /**
	 * Return an uncommitted role entity
	 * 
	 * @return
	 * @throws Exception
	 */
    public JsonRes newRoleJson(CallObject callObj) throws Exception {
    	EntRole o = newRole(callObj);
    	List<JsonRole> y = new ArrayList<>();
    	y.add(o.toJSon());
		return new JsonRes().setData(y);
    }
  
    /**
	 * Return an uncommitted role entity
	 * 
	 * @return
	 * @throws Exception
	 */
    public EntRole newRole(CallObject callObj) throws Exception {
    	EntRole o = new EntRole();
    	return o.setId(dao.nextTempIdNegative())
    			.setOrgNr(BASE_ORG_NR)
		    	.setActive()
		    	;
    }  
    
    /**
	 * Create / Update / Delete the role list
	 * 
	 * @param callObj
	 * @param entities to do CrUD on
	 * @throws Exception
	 */
    public JsonRes putRoles(
    		CallObject callObj,
    		List<EntRole> list) throws Exception {
		
		
		EntityConfig roleConfig = configSrv.getConfig(callObj, EntRole.class);
		EntityConfig permConfig = configSrv.getConfig(callObj, EntRolePermission.class);
		
		//Validation
		ValidationErrors vals = new ValidationErrors();
		for (EntRole ent : list) {
			dao.compareTimeStamp(ent, roleConfig, vals);
//			dao.validate(ent, roleConfig, vals);
//			
//			for (EntRolePermission perm : ent.getPermissions()) {
//				dao.validate(perm, permConfig, vals);
//  		}
		}

		//Validation Errors
		if (vals.hasErrors()) {
			return new JsonRes()
					.setError("invlist")
					.setReturnCode(JS_VALIDATION_ERRORS)
					.setData(vals);
		}
		
		List<Long []> ids = new ArrayList<>();
  		try {
  			for (EntRole ent : list) {
  				//Special case
  				if (ent.isNew()) {
  					for (EntRolePermission perm : ent.getPermissions()) {
  						nullBaseFields(perm, permConfig);
  	  					perm.setId(null)
  	  					    .setEntRole(ent);
  	  	  			}
  				}
  				
  				dao.put(ent, roleConfig, callObj);
  				
  				if (ent.isDelete()) {
  					continue;
  				}
  				if (ent.getTempId() != null) {
  					Long id[] = {ent.getTempId(), ent.getId()};
  					ids.add(id);
  					continue;
  				}
  				
  				//Children
  				for (EntRolePermission perm : ent.getPermissions()) {
  					perm.setEntRole(ent);
  					dao.put(perm, permConfig, callObj);
  	  			}	
  			}
  			
  		} catch (Exception e) {
  			log.error(e);
  			throw e;
  		}
  		
  		return new JsonRes().setData(ids);
  	}  


    /**
	 * Export roles to excel
	 * @return
	 * @throws Exception
	 */
	public Response excelExport(CallObject callObj) throws Exception {
		
		List<EntRole> x = dao.roleList(callObj, null);
		UtilLabel labels = langSrv.getLabelUtil(callObj.getOrgNr(), null, callObj.getLang(), null);
		ExcelRole excel = new ExcelRole(labels, x);
		
		String fn = excelSrv.createListFile("RoleList", callObj.getOrgNr(), excel);
		return fileSrv.getFile(fn, "RoleList.xlsx", false);
    }
    
}
