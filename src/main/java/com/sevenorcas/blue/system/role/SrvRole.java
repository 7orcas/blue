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
import com.sevenorcas.blue.system.field.validationDEL.Validation;
import com.sevenorcas.blue.system.lang.ent.UtilLabel;
import com.sevenorcas.blue.system.lifecycle.CallObject;
import com.sevenorcas.blue.system.role.ent.EntRole;
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
	public JsonRes permissonListJson(
			CallObject callObj,
    		SqlParm parms) throws Exception{
		
		List<EntRole> x = dao.roleList(callObj, parms);
		List<JsonRole> y = new ArrayList<>();
		for (EntRole d : x) {
			y.add(d.toJSon());
		}
		
		return new JsonRes().setData(y);
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
		
		List<Validation> vals = new ArrayList<>();
		List<Long []> ids = new ArrayList<>();
		
		
//		//Validation
//		for (EntRole ent : list) {
//			if (!ent.isValidEntity()) {
//				vals.add(ent.getValidation());
//			}
//		}
		
		//Errors
		if (vals.size() > 0) {
			return new JsonRes().setError("invlist").setData(vals);
		}
		
  		try {
  			for (EntRole ent : list) {
  				
  				if (ent.isDelete()) {
  					dao.deleteRole(ent.getId());
  				}
  				else if (ent.isValidId()) {
  					dao.mergeRole (ent);
  				}
  				else if (ent.isNew()){
  					Long[] id = new Long[2];
  					ids.add(id);
  					id[0] = ent.getId();
  					ent.setId(null);
  					id[1] = dao.persistRole(ent);
  				}
  			}
  			
  		} catch (Exception e) {
  			log.equals(e);
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
